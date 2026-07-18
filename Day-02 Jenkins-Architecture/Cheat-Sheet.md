# 🚀 Jenkins Learning Series – Day 2

# Jenkins Architecture Cheat Sheet

Quick Revision Guide | Interview Notes | Production Ready

---

# Jenkins Architecture

Jenkins follows a **Controller-Agent Architecture**.

- Controller manages Jenkins.
- Agents execute build jobs.
- Executors run builds.
- Workspace stores project files.
- Build Queue holds waiting jobs.

---

# Architecture Diagram

```
                    Developer
                         │
                         ▼
                  GitHub Repository
                         │
                    Webhook Trigger
                         │
                         ▼
                Jenkins Controller
                         │
          Schedules & Assigns Jobs
                         │
      ┌──────────────────┼──────────────────┐
      │                  │                  │
      ▼                  ▼                  ▼
 Linux Agent       Windows Agent      Docker Agent
      │                  │                  │
      ▼                  ▼                  ▼
 Build Java        Build .NET       Build Containers
                         │
                         ▼
                     Deployment
```

---

# Jenkins Components

| Component | Responsibility |
|-----------|----------------|
| Controller | Manages Jenkins |
| Agent | Executes build jobs |
| Executor | Executes one build at a time |
| Workspace | Stores build files |
| Build Queue | Holds waiting jobs |
| Labels | Select appropriate Agents |
| Plugins | Extend Jenkins functionality |
| Credentials | Store secrets securely |

---

# Controller Responsibilities

- Hosts Jenkins UI
- Schedules Jobs
- Stores Credentials
- Manages Plugins
- Maintains Build Queue
- Assigns Jobs to Agents
- Stores Build History

**Production Tip:**

Never execute heavy builds on the Controller.

---

# Jenkins Agents

Agents perform the actual build work.

Examples:

- Linux
- Windows
- Docker
- Kubernetes
- AWS EC2
- Azure VM

---

# Executors

**One Executor = One Running Build**

Example

```
Linux Agent

Executor-1 → Build #101

Executor-2 → Build #102

Executor-3 → Idle
```

More Executors = More Parallel Builds

---

# Workspace

Workspace stores:

- Source Code
- Dependencies
- Build Files
- Reports
- Artifacts

Linux

```
/var/lib/jenkins/workspace/
```

Windows

```
C:\ProgramData\Jenkins\.jenkins\workspace\
```

---

# Build Queue

When all Executors are busy:

```
New Build

↓

Build Queue

↓

Available Executor

↓

Build Starts
```

---

# Complete Build Flow

```
Developer

↓

GitHub

↓

Webhook

↓

Controller

↓

Queue

↓

Agent

↓

Workspace

↓

Checkout

↓

Compile

↓

Unit Tests

↓

SonarQube

↓

Package

↓

Docker Build

↓

Push Image

↓

Deploy

↓

Notification
```

---

# Controller ↔ Agent Communication

| Method | Best For |
|---------|----------|
| SSH | Linux Servers |
| JNLP | Cloud & Enterprise |
| WebSocket | Modern Jenkins |

---

# Single Node vs Distributed Jenkins

| Single Node | Distributed |
|--------------|-------------|
| One Server | Multiple Agents |
| Slow | Faster |
| Limited Scaling | Highly Scalable |
| Sequential Builds | Parallel Builds |
| Learning | Production |

---

# Production Best Practices

- Keep Controller lightweight
- Execute builds only on Agents
- Use Labels effectively
- Backup JENKINS_HOME
- Secure Credentials
- Clean Workspace regularly
- Monitor Agents
- Update Plugins
- Use Pipeline as Code
- Use Docker/Kubernetes Agents
- Archive only required artifacts

---

# Common Mistakes

❌ Running builds on Controller

❌ Hardcoding passwords

❌ Too many Executors

❌ Never cleaning Workspace

❌ Ignoring offline Agents

❌ Installing unnecessary plugins

---

# Frequently Asked Interview Questions

1. What is Jenkins Controller?

2. What is Jenkins Agent?

3. What is an Executor?

4. What is Workspace?

5. What is Build Queue?

6. Why use Distributed Jenkins?

7. Difference between Controller and Agent?

8. SSH vs JNLP?

9. Why use Labels?

10. Explain Jenkins Architecture.

---

# Controller vs Agent

| Controller | Agent |
|------------|-------|
| Manages Jenkins | Executes Builds |
| Schedules Jobs | Runs Pipelines |
| Stores Credentials | Uses Credentials |
| Maintains Queue | Performs Tasks |
| Stores Build History | Returns Build Results |

---

# Quick Revision

```
Controller

↓

Queue

↓

Agent

↓

Executor

↓

Workspace

↓

Build

↓

Test

↓

Package

↓

Deploy
```

Remember:

✔ Controller manages.

✔ Agents execute.

✔ Executors run builds.

✔ Workspace stores project files.

✔ Queue waits for free Executors.

✔ Distributed Jenkins = Production Ready.

---

# Key Commands

Check Workspace (Linux)

```bash
ls /var/lib/jenkins/workspace/
```

Check Jenkins Home

```bash
echo $JENKINS_HOME
```

Example Build Command

```bash
mvn clean package
```

Build Docker Image

```bash
docker build -t myapp:v1 .
```

Deploy to Kubernetes

```bash
kubectl apply -f deployment.yaml
```

---

# Summary

- Jenkins uses Controller-Agent Architecture.
- Controller manages Jenkins.
- Agents execute build jobs.
- Executors enable parallel execution.
- Workspace stores build files.
- Build Queue manages waiting jobs.
- Distributed Jenkins improves scalability.
- Follow production best practices for reliable CI/CD.