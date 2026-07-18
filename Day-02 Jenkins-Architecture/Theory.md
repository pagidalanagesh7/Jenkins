# Jenkins Architecture — Complete Theory

---

## 📖 What is Jenkins Architecture?

Jenkins follows a **Controller-Agent (Master-Slave) architecture**. It is designed so that one central brain (Controller) coordinates work, while the actual heavy-lifting (builds, tests, deployments) can be delegated to one or many worker machines (Agents).

Why this design matters:
- A single Jenkins server handling everything (UI, scheduling, AND running builds) does not scale — CPU/memory gets exhausted fast when 50 pipelines run in parallel.
- By splitting **orchestration** (Controller) from **execution** (Agents), Jenkins can scale horizontally — just add more agents.
- This is the same principle Kubernetes uses (control-plane vs worker nodes) — if you know K8s, Jenkins architecture will feel very familiar.

**Core idea in one line:**
> Controller decides *what* to run and *when*. Agents actually *do* the running.

```
┌─────────────────────────────────────────────┐
│              JENKINS CONTROLLER              │
│  (UI, Scheduler, Build Queue, Plugins, Config)│
└───────────────────┬───────────────────────────┘
                     │ dispatches jobs via
                     │ SSH / JNLP / WebSocket
        ┌────────────┼────────────┐
        ▼            ▼            ▼
   ┌─────────┐  ┌─────────┐  ┌─────────┐
   │ Agent 1 │  │ Agent 2 │  │ Agent 3 │
   │ (Linux) │  │ (Win)   │  │ (Docker)│
   └─────────┘  └─────────┘  └─────────┘
```

---

## 🏗️ Jenkins Controller (Master)

The Controller (older docs call it "Master") is the central management node of Jenkins.

**Responsibilities:**
- Hosts the Jenkins web UI (dashboard, job configs, logs).
- Stores all configuration — jobs, credentials, plugins, global settings (in `$JENKINS_HOME`).
- Schedules builds and manages the **Build Queue**.
- Decides which Agent should run which job (based on labels).
- Monitors Agent health (online/offline).
- Aggregates and displays build results, test reports, artifacts.

**What Controller should NOT do (best practice):**
- Should **not** run production builds directly on itself. Reason: if a rogue build eats all CPU/RAM, the entire Jenkins UI + scheduler goes down for everyone.

**Key directory:** `JENKINS_HOME` — usually `/var/lib/jenkins` on Linux. Contains:
```
JENKINS_HOME/
├── config.xml          # global config
├── jobs/                # each job's config + build history
├── plugins/             # installed plugins
├── secrets/             # credentials store
├── nodes/                # agent definitions
└── workspace/            # (only if builds run on controller)
```

---

## 💻 Jenkins Agents (Nodes)

Agents (formerly "Slaves") are the machines that actually execute the build steps defined in your `Jenkinsfile` or job config.

**Key facts:**
- An agent can be a physical server, a VM, a Docker container, or a Kubernetes pod (spun up dynamically).
- Each agent is registered with the Controller and given a **name + labels** (e.g., `linux`, `docker`, `maven`, `high-mem`).
- Pipelines request an agent using labels:

```groovy
pipeline {
    agent { label 'docker && linux' }
    stages {
        stage('Build') {
            steps { sh 'mvn clean install' }
        }
    }
}
```

**Agent connection modes:**
| Mode | How it connects | Typical use |
|---|---|---|
| SSH | Controller SSHes into agent | Static Linux servers |
| JNLP (Java Web Start) | Agent connects out to Controller | Agents behind firewall/NAT |
| Kubernetes Plugin | Controller creates a pod dynamically | Ephemeral, auto-scaling agents |
| Docker | Controller spins up a container as agent | Isolated, disposable build envs |

**Example: Agent goes offline mid-build**
```
Build #42 started on agent 'ec2-linux-04'
[ERROR] Agent 'ec2-linux-04' disconnected — reason: Ping failed after 4 attempts
Build #42 marked as ABORTED
```
This is exactly why production setups use **multiple agents with the same label** — Jenkins can retry on a different agent.

---

## ⚙️ Executors

An **Executor** is a slot on an agent (or controller) capable of running ONE build step at a time.

- One agent can have multiple executors (e.g., 4 executors = 4 builds can run in parallel on that single machine).
- Executors are just threads — they don't guarantee isolation (unlike Docker/K8s agents, which give a fresh container per build).

**Example — capacity planning:**
```
Agent: ec2-linux-04 (8 vCPU, 16GB RAM)
Executors configured: 4

→ Up to 4 builds run simultaneously on this one agent.
→ If job #5 arrives while all 4 are busy, it WAITS in the Build Queue.
```

**Rule of thumb:** executors ≈ number of vCPUs (never blindly max it out — heavy builds like Maven/Docker builds can starve each other for RAM even if CPU allows it).

---

## 📂 Workspace

The **Workspace** is a dedicated directory on the agent where your repo is checked out and where the build actually happens.

- Default path: `$JENKINS_HOME/workspace/<job-name>` (on that specific agent, not the controller, unless job runs on controller).
- Each job gets its own workspace — no interference between jobs (unless you explicitly reuse one).
- Workspace persists between builds **on that agent** unless you clean it — this is why builds can behave differently on different agents (leftover files, cached dependencies).

**Example path on an agent:**
```
/home/jenkins/workspace/watsonx-api-pipeline/
├── src/
├── pom.xml
├── target/            # build artifacts land here
└── .git/
```

**Best practice:** Always clean workspace at the start (`cleanWs()`) for reproducible builds:
```groovy
stage('Checkout') {
    steps {
        cleanWs()
        git branch: 'main', url: 'https://github.com/org/repo.git'
    }
}
```

---

## 📦 Build Queue

The **Build Queue** is where jobs wait when no suitable executor is free.

**How a job enters and leaves the queue:**
1. Trigger fires (SCM webhook, cron, manual "Build Now", upstream job).
2. Job enters queue.
3. Jenkins scheduler checks: is there an agent matching the required label AND with a free executor?
4. If yes → job leaves queue, starts running.
5. If no → job stays queued, scheduler re-checks periodically.

**Example queue state (visible on `/queue` in Jenkins UI):**
```
Build Queue (3)
1. watsonx-api-pipeline #101   — Waiting for next available executor on 'docker'
2. terraform-apply #45         — Waiting for next available executor on 'linux'
3. helm-deploy-prod #12        — (Locked) waiting for resource 'prod-deploy-lock'
```

Common reasons jobs stay stuck in queue:
- No agent matches the label.
- All matching agents' executors are busy.
- A `lock`/`throttle` plugin is blocking concurrent execution (e.g., only 1 prod deploy at a time).

---

## 🔄 Complete Jenkins Build Flow

Step-by-step, from commit to deployed artifact:

```
1. Developer pushes code to GitHub
        │
        ▼
2. GitHub Webhook notifies Jenkins Controller
        │
        ▼
3. Controller creates a new Build and puts it in the Build Queue
        │
        ▼
4. Scheduler finds a matching Agent (label match + free executor)
        │
        ▼
5. Controller sends build instructions to Agent (via SSH/JNLP)
        │
        ▼
6. Agent checks out code into its Workspace
        │
        ▼
7. Agent runs pipeline stages: Build → Test → Package
        │
        ▼
8. Agent streams console logs back to Controller in real-time
        │
        ▼
9. Artifacts (jar/war/docker image) archived / pushed to registry
        │
        ▼
10. Controller updates Build UI: SUCCESS / FAILURE / UNSTABLE
        │
        ▼
11. Post-build actions fire: Slack notify, email, trigger downstream job
```

**Sample console output for a real pipeline run:**
```
Started by GitHub push by nagesh-devops
Running on ec2-linux-agent-02 in /home/jenkins/workspace/watsonx-api

[Pipeline] stage (Checkout)
+ git clone https://github.com/org/watsonx-api.git
Cloning into 'watsonx-api'... done.

[Pipeline] stage (Build)
+ mvn clean package
[INFO] BUILD SUCCESS
[INFO] Total time: 42.318 s

[Pipeline] stage (Test)
+ mvn test
Tests run: 87, Failures: 0, Errors: 0, Skipped: 2

[Pipeline] stage (Docker Build & Push)
+ docker build -t registry.ibm.com/watsonx-api:build-101 .
+ docker push registry.ibm.com/watsonx-api:build-101

Finished: SUCCESS
```

---

## 🔗 Controller ↔ Agent Communication

Two main protocols used:

**1. SSH (Controller → Agent, outbound)**
- Controller initiates connection to the agent.
- Requires controller to have SSH key access to the agent.
- Good for: static, always-on Linux servers you control (e.g., on-prem VMs).

**2. JNLP / Inbound Agent (Agent → Controller, outbound)**
- Agent initiates connection to controller on a fixed TCP port (default 50000).
- Good for: agents sitting behind NAT/firewall where controller can't reach them directly (common in enterprise networks — like IBM internal clusters).

```
SSH mode:                          JNLP mode:
Controller ──SSH──▶ Agent          Agent ──JNLP/TCP:50000──▶ Controller
(Controller must reach agent)      (Agent must reach controller)
```

**3. Kubernetes Plugin (dynamic, no persistent connection)**
- Controller talks to the K8s API server, creates a Pod with a JNLP container as agent.
- Once the build finishes, the pod is deleted automatically.
- Best for cost + isolation — you never pay for idle agents.

```yaml
# Example Kubernetes agent pod template (Jenkins Kubernetes plugin)
podTemplate(label: 'docker-agent', containers: [
  containerTemplate(name: 'maven', image: 'maven:3.9-eclipse-temurin-17', command: 'sleep', args: '99999')
]) {
    node('docker-agent') {
        stage('Build') {
            container('maven') {
                sh 'mvn clean install'
            }
        }
    }
}
```

---

## ☁️ Distributed Jenkins Architecture

For enterprise scale, Jenkins is deployed as a **distributed system**:

```
                     ┌─────────────────────────┐
                     │   Jenkins Controller      │
                     │ (HA pair / active-passive)│
                     └────────────┬───────────────┘
                                  │
        ┌─────────────┬──────────┼──────────┬─────────────┐
        ▼             ▼          ▼          ▼             ▼
  ┌───────────┐ ┌───────────┐ ┌────────┐ ┌────────┐ ┌───────────┐
  │ Static VM │ │ Static VM │ │  EKS   │ │  EKS   │ │  Windows  │
  │  Agents   │ │  Agents   │ │  Pod   │ │  Pod   │ │  Agents   │
  │ (label:   │ │ (label:   │ │ Agents │ │ Agents │ │ (label:   │
  │  legacy)  │ │  terraform)│ │(docker)│ │(docker)│ │  .net)    │
  └───────────┘ └───────────┘ └────────┘ └────────┘ └───────────┘
```

**Why distributed setup matters at enterprise scale (e.g., IBM Watsonx-style platform):**
- Different teams need different environments (Java 8 vs Java 17, Windows .NET builds, GPU nodes for ML).
- Auto-scaling agents on EKS means you're not paying for idle EC2 instances 24/7.
- HA Controller (active-passive with shared `JENKINS_HOME` on EFS/NFS) avoids a single point of failure.
- Agents can live in different AWS regions/VPCs closer to the resources they build against.

**Typical AWS-based distributed Jenkins stack:**
- Controller → EC2 (or EKS deployment) with EFS-backed `JENKINS_HOME`.
- Agents → EKS pods (Kubernetes plugin) auto-scaled via Cluster Autoscaler / Karpenter.
- Artifacts → S3 or ECR.
- Secrets → AWS Secrets Manager / Jenkins Credentials Store.
- Logs/metrics → CloudWatch + Prometheus/Grafana.

---

## 🏢 Real Enterprise Jenkins Workflow

A realistic CI/CD flow you'd see on a platform team (e.g., Watsonx-style):

```
1. Dev raises PR → GitHub Actions runs lint/unit tests (fast feedback)
2. PR merged to main → GitHub webhook triggers Jenkins Controller
3. Jenkins queues job with label 'eks-docker-agent'
4. K8s plugin spins up a fresh EKS pod as Jenkins agent
5. Pipeline stages run:
     - Checkout
     - Static code analysis (SonarQube)
     - Unit + Integration tests
     - Build Docker image
     - Push image to ECR (tagged with build number + git SHA)
     - Trigger Helm chart update (values.yaml image tag bump)
     - ArgoCD auto-syncs the new image to EKS (GitOps)
6. Post-build: Slack notification + Prometheus metric pushed
7. EKS pod (Jenkins agent) is deleted — zero idle cost
```

**Sample Jenkinsfile matching this flow:**
```groovy
pipeline {
    agent { label 'eks-docker-agent' }
    environment {
        ECR_REPO = '123456789.dkr.ecr.us-east-1.amazonaws.com/watsonx-api'
        IMAGE_TAG = "${env.BUILD_NUMBER}-${GIT_COMMIT.take(7)}"
    }
    stages {
        stage('Checkout') {
            steps { cleanWs(); checkout scm }
        }
        stage('Test') {
            steps { sh 'mvn clean test' }
        }
        stage('Docker Build & Push') {
            steps {
                sh """
                  docker build -t $ECR_REPO:$IMAGE_TAG .
                  aws ecr get-login-password | docker login --username AWS --password-stdin $ECR_REPO
                  docker push $ECR_REPO:$IMAGE_TAG
                """
            }
        }
        stage('Update Helm values (GitOps)') {
            steps {
                sh """
                  yq -i '.image.tag = \"$IMAGE_TAG\"' helm/values.yaml
                  git commit -am "Update image tag to $IMAGE_TAG"
                  git push origin main
                """
            }
        }
    }
    post {
        success { slackSend(channel: '#ci-cd', message: "✅ Build $IMAGE_TAG deployed") }
        failure { slackSend(channel: '#ci-cd', message: "❌ Build $BUILD_NUMBER failed") }
    }
}
```

---

## 💡 Production Best Practices

1. **Never build on the Controller** — set `Number of executors = 0` on the built-in node.
2. **Use ephemeral agents** (Docker/Kubernetes) for stateless, reproducible builds — no "works on this agent only" bugs.
3. **Label agents precisely** (`docker && java17 && high-mem`) so jobs land on the right hardware.
4. **Clean workspace** at the start of every pipeline (`cleanWs()`), especially on reused static agents.
5. **Secure the Controller** — restrict who can edit Jenkinsfiles/pipeline scripts (script approval, RBAC via Role Strategy plugin).
6. **Store credentials in Jenkins Credentials Store / AWS Secrets Manager**, never hardcode in Jenkinsfile.
7. **Set build timeouts** (`timeout(time: 30, unit: 'MINUTES')`) — a stuck build should not hog an executor forever.
8. **Enable HA for Controller** in critical environments — shared `JENKINS_HOME` on EFS + active-passive failover.
9. **Archive only what's needed** — don't archive huge build dirs; push real artifacts to S3/ECR/Nexus.
10. **Monitor executor utilization** — if the queue is always backed up, add more agents or executors, don't just wait.
11. **Version your Jenkinsfile** in the same repo as the code (Pipeline-as-Code) — never configure jobs manually via UI for anything production-facing.
12. **Use `Retry` and `Lock` plugins** for flaky steps and for serializing prod deployments (avoid two deploys colliding).

---

## 🎯 Interview Questions

**Q1. What is the difference between Jenkins Controller and Agent?**
> Controller is the brain — it schedules jobs, stores config, hosts UI, and decides *where* to run builds. Agent is the muscle — it actually executes the build steps in its own workspace. Controller should never run heavy builds itself in production.

**Q2. If your Jenkins builds are slow and the Build Queue keeps growing, how do you troubleshoot?**
> First check: are there enough executors/agents matching the job's label? Check `/queue` for the "why is this stuck" message. If it's a label mismatch, fix labels. If executors are simply saturated, either add more agents (or increase executors carefully) or switch to dynamic K8s agents for auto-scaling.

**Q3. Why use Kubernetes-based dynamic agents instead of static EC2 agents?**
> Dynamic agents (via K8s plugin) spin up only when needed and get destroyed after the build — no idle cost, guaranteed clean environment every time, and effectively infinite horizontal scale as long as the EKS cluster can add nodes.

**Q4. What happens to a running build if its agent goes down mid-execution?**
> The build typically gets marked `ABORTED` (connection lost), since the executor/workspace it was using is gone. Best practice is to design pipelines idempotently and let retry mechanisms/upstream triggers re-run the job on a healthy agent.

**Q5. How do you secure secrets (like AWS keys, DB passwords) in a Jenkins pipeline?**
> Use the Jenkins Credentials Store (or better, AWS Secrets Manager / HashiCorp Vault integration) and reference them via `withCredentials{}` block — never hardcode secrets in the Jenkinsfile or echo them in logs.

**Q6. What's the difference between SSH and JNLP agent connection modes?**
> SSH: Controller initiates connection to agent (agent must be reachable from controller). JNLP: Agent initiates connection to controller (useful when agent is behind firewall/NAT and can't be reached directly — controller just needs an open inbound port, typically 50000).

**Q7. Why should the number of executors on the Controller be set to 0 in production?**
> To prevent regular build jobs from ever running on the Controller itself. If a build consumes all Controller CPU/RAM, the entire Jenkins UI and scheduler for all users/teams goes down — a single bad build can take down all of Jenkins.

**Q8. How would you design Jenkins architecture for a multi-team, multi-environment enterprise setup (like IBM Watsonx)?**
> HA Controller pair with shared `JENKINS_HOME` on EFS/NFS; agent pools split by capability (EKS pods for containerized builds, Windows static agents for .NET, GPU nodes for ML workloads) each tagged with clear labels; centralized credentials via Secrets Manager; artifacts to ECR/S3; GitOps (ArgoCD) for actual deployment so Jenkins only builds/pushes, it doesn't directly `kubectl apply` to prod.

---

## 📝 Summary

- Jenkins uses a **Controller-Agent architecture**: Controller orchestrates, Agents execute.
- **Executors** are parallel build slots on an agent; **Workspace** is where the actual code/build files live per job.
- Jobs wait in the **Build Queue** until a matching agent + free executor is available.
- Communication happens via **SSH**, **JNLP**, or dynamically via the **Kubernetes plugin** (best for auto-scaling, zero idle cost).
- Enterprise setups go **distributed**: HA controllers, labeled agent pools (Docker/K8s/Windows/static VMs), artifacts to S3/ECR, and GitOps-based deployment (ArgoCD) instead of Jenkins directly touching prod.
- Golden rules: never build on the controller, always use Pipeline-as-Code, keep agents ephemeral where possible, and secure your credentials properly.

> Master this flow — commit → webhook → queue → agent → workspace → build → artifact → deploy — and you can explain Jenkins architecture confidently in any interview, at any level of depth they ask for.
