# 🚀 Jenkins Learning Series – Day 2

## Jenkins Architecture Explained

## 📖 Introduction

Jenkins is one of the most popular open-source automation servers used to implement **Continuous Integration (CI)** and **Continuous Delivery/Continuous Deployment (CD)**. It automates repetitive software development tasks such as building, testing, packaging, and deploying applications.

In small projects, Jenkins can run everything on a single machine. However, in enterprise environments where hundreds of developers trigger builds every day, a single server becomes a bottleneck.

To solve this problem, Jenkins uses a **Controller-Agent Architecture**, where the **Controller** manages Jenkins and **Agents** execute build jobs. This design improves scalability, performance, and reliability, making Jenkins suitable for production CI/CD pipelines.

---

# 🏗 What is Jenkins Architecture?

Jenkins Architecture is the internal design that defines **how Jenkins receives, schedules, and executes build jobs**.

Instead of performing all tasks on one machine, Jenkins separates responsibilities into two main components:

- **Controller** – Manages Jenkins and schedules jobs.
- **Agents** – Execute the actual build, test, and deployment tasks.

This separation allows multiple builds to run simultaneously and makes Jenkins highly scalable.

---

# 📌 High-Level Architecture

```
                    Developer
                         │
                  Push Source Code
                         │
                         ▼
                  Git Repository
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
                   Deployment Stage
```

---

# 🧩 Core Components

| Component | Responsibility |
|-----------|----------------|
| **Controller** | Manages Jenkins, schedules jobs, stores configurations |
| **Agent** | Executes build jobs assigned by the Controller |
| **Executor** | Runs one build at a time |
| **Workspace** | Stores project source code and build files |
| **Build Queue** | Holds jobs waiting for an available Executor |

These components work together to automate the complete CI/CD workflow.

---

# 💡 Real-World Analogy

Think of a software company.

| Real World | Jenkins |
|------------|----------|
| Project Manager | Controller |
| Developers/Engineers | Agents |
| Assigned Task | Build Job |
| Office | Jenkins Environment |

The **Project Manager** assigns work but doesn't complete every task personally.

Similarly, the **Jenkins Controller** schedules jobs, while the **Agents** perform the actual work.

---

# 🎯 Interview Tip

One of the most common interview questions is:

**Why does Jenkins use a Controller-Agent Architecture?**

**Answer:**
> It separates management from execution, allowing builds to run on multiple machines simultaneously. This improves scalability, performance, resource utilization, and reliability in production environments.

---

# 📝 Key Takeaways

- Jenkins follows a **Controller-Agent Architecture**.
- The **Controller** manages Jenkins and schedules jobs.
- **Agents** execute build, test, and deployment tasks.
- This architecture enables **distributed and parallel builds**.
- It is the recommended setup for enterprise CI/CD environments.

# ⚙️ Core Components of Jenkins

Jenkins Architecture consists of five core components that work together to automate the complete CI/CD pipeline. Understanding these components is essential before creating production-ready pipelines.

---

## 1️⃣ Jenkins Controller

The **Controller** is the central server that manages the entire Jenkins environment. It schedules jobs, stores configurations, maintains the build queue, and assigns work to available Agents.

### Responsibilities

- Hosts Jenkins Web UI
- Schedules build jobs
- Manages plugins
- Stores credentials
- Maintains build history
- Assigns jobs to Agents

### Diagram

```text
Developer
     │
     ▼
Jenkins Controller
     │
Assign Job
     ▼
Agent
```

> 💡 **Interview Tip:** In production, the Controller should only manage Jenkins. Heavy builds should always run on Agents.

---

## 2️⃣ Jenkins Agent (Node)

A **Jenkins Agent** is a machine that executes the jobs assigned by the Controller.

Agents can be:

- Linux Server
- Windows Server
- Docker Container
- Kubernetes Pod
- Cloud Virtual Machine

### Why Agents?

Instead of overloading one server, Jenkins distributes builds across multiple Agents.

```text
            Controller
                │
      ┌─────────┼─────────┐
      ▼         ▼         ▼
 Linux      Windows     Docker
 Agent        Agent      Agent
```

This enables parallel execution and improves scalability.

---

## 3️⃣ Executor

An **Executor** is a worker process inside an Agent.

> **One Executor = One Running Build**

Example:

```text
Linux Agent

Executor-1 → Build #101

Executor-2 → Build #102

Executor-3 → Idle
```

If all Executors are busy, new jobs wait in the Build Queue.

---

## 4️⃣ Workspace

A **Workspace** is the working directory where Jenkins performs all build activities.

It contains:

- Source Code
- Dependencies
- Build Files
- Test Reports
- Generated Artifacts

Example (Linux)

```text
/var/lib/jenkins/workspace/
```

Typical Build Process

```text
Git Clone
     │
Compile
     │
Run Tests
     │
Package
     │
Generate Artifact
```

Keeping the Workspace clean helps save disk space and improves build performance.

---

## 5️⃣ Build Queue

The **Build Queue** stores jobs that are waiting for an available Executor.

Example:

```text
Developer Push

        │

        ▼

 Build Queue

        │

Waiting...

        │

        ▼

Available Executor

        │

        ▼

Build Starts
```

The queue ensures that builds are executed in an organized manner without overloading the system.

---

# 📊 Component Summary

| Component | Purpose |
|-----------|---------|
| Controller | Manages Jenkins and schedules jobs |
| Agent | Executes build jobs |
| Executor | Runs one build at a time |
| Workspace | Stores source code and build files |
| Build Queue | Holds waiting jobs |

---

# 📝 Key Takeaways

- The **Controller** manages Jenkins.
- **Agents** execute the actual build jobs.
- **Executors** allow parallel builds.
- The **Workspace** stores project files during execution.
- The **Build Queue** manages pending jobs until an Executor becomes available.

Together, these five components form the foundation of every Jenkins CI/CD pipeline.

# 🔄 Complete Jenkins Build Flow

A Jenkins pipeline starts when a developer pushes code to a Git repository. Jenkins then automates the entire software delivery process—from source code checkout to deployment and notifications.

---

## 🚀 End-to-End Build Flow

```text
                👨‍💻 Developer
                      │
              git push origin main
                      │
                      ▼
             GitHub / GitLab Repository
                      │
                 Webhook Trigger
                      │
                      ▼
             Jenkins Controller
                      │
             Checks Build Queue
                      │
                      ▼
            Available Jenkins Agent
                      │
              Creates Workspace
                      │
                      ▼
          Checkout Source Code
                      │
                      ▼
             Compile Application
                      │
                      ▼
              Run Unit Tests
                      │
                      ▼
         Static Code Analysis
               (SonarQube)
                      │
                      ▼
          Package Application
                      │
                      ▼
          Build Docker Image
                      │
                      ▼
     Push Image to Docker Registry
                      │
                      ▼
      Deploy to Kubernetes / Server
                      │
                      ▼
        Slack / Teams / Email Alert
```

---

## 📋 Build Workflow Explained

### 1. Developer Pushes Code

A developer commits code and pushes it to the Git repository.

```bash
git add .
git commit -m "Added Login Feature"
git push origin main
```

---

### 2. Webhook Triggers Jenkins

GitHub sends a webhook to Jenkins immediately after the push.

This eliminates continuous polling and starts the pipeline instantly.

---

### 3. Controller Creates the Build

The Jenkins Controller:

- Reads the Jenkinsfile
- Creates a new build
- Checks the Build Queue
- Finds an available Agent

If no Executor is available, the job waits in the queue.

---

### 4. Agent Executes the Pipeline

The selected Agent performs all build activities inside its Workspace.

Typical steps include:

- Checkout source code
- Install dependencies
- Compile the application
- Run tests

---

### 5. Code Quality Check

Many organizations integrate **SonarQube** to verify code quality.

Typical checks include:

- Bugs
- Vulnerabilities
- Code Smells
- Duplicated Code

A failed Quality Gate usually stops the pipeline.

---

### 6. Package the Application

If all tests pass, Jenkins packages the application.

Examples:

```bash
mvn clean package
```

```bash
npm run build
```

Generated artifacts may include:

- JAR
- WAR
- ZIP

---

### 7. Build & Push Docker Image

For containerized applications, Jenkins creates a Docker image.

```bash
docker build -t payment-service:v1 .
docker push company/payment-service:v1
```

The image is then pushed to a container registry such as:

- Docker Hub
- AWS ECR
- Azure ACR
- Harbor

---

### 8. Deploy Application

The final stage deploys the application to the target environment.

Example:

```bash
kubectl apply -f deployment.yaml
```

Deployment targets may include:

- Kubernetes
- Docker Swarm
- Virtual Machines
- Cloud Platforms

---

### 9. Send Notifications

After the pipeline completes, Jenkins sends the build status.

Example:

```text
Build #125

Status : SUCCESS ✅

Duration : 4m 32s

Branch : main
```

Notifications can be sent to:

- Slack
- Microsoft Teams
- Email
- Discord

---

## 🌍 Real Enterprise Workflow

```text
Developer
     │
     ▼
GitHub
     │
Webhook
     ▼
Jenkins
     │
Linux Agent
     ▼
Build
     ▼
Test
     ▼
SonarQube
     ▼
Docker Build
     ▼
Docker Registry
     ▼
Kubernetes
     ▼
Production
     ▼
Slack Notification
```

This is one of the most common CI/CD workflows used in modern DevOps environments.

---

## 💡 Interview Tip

**Question:** What happens after a developer pushes code to GitHub?

**Answer:**

1. GitHub sends a webhook to Jenkins.
2. Jenkins Controller creates a build.
3. An available Agent executes the pipeline.
4. The application is built, tested, and packaged.
5. A Docker image is created and pushed.
6. The application is deployed.
7. Jenkins sends a success or failure notification.

---

## 📝 Key Takeaways

- A Git push starts the Jenkins pipeline.
- Webhooks trigger builds automatically.
- The Controller schedules the job.
- Agents execute the pipeline.
- Jenkins automates Build → Test → Package → Deploy.
- Notifications are sent after pipeline completion.

# ☁️ Enterprise Jenkins Architecture

Large organizations don't run Jenkins on a single server. Instead, they use a **Distributed Jenkins Architecture**, where one Controller manages multiple Agents.

This approach improves performance, scalability, reliability, and enables multiple teams to build applications simultaneously.

---

## 🏢 Single Node vs Distributed Jenkins

### Single Node Architecture

In a single-node setup, the Controller performs every task.

```text
          Jenkins Server

     Controller + Build Server
              │
      Build → Test → Deploy
```

### Advantages

- Easy to install
- Good for learning
- Low infrastructure cost

### Limitations

- Limited scalability
- No parallel builds
- Single point of failure
- Not suitable for production

---

### Distributed Jenkins Architecture

In production, build execution is distributed across multiple Agents.

```text
                  Jenkins Controller
                         │
      ┌──────────────────┼──────────────────┐
      │                  │                  │
      ▼                  ▼                  ▼
 Linux Agent      Windows Agent      Docker Agent
      │                  │                  │
 Java Builds      .NET Builds      Container Builds
      │                  │                  │
      └──────────────────┼──────────────────┘
                         │
                         ▼
                  Deployment Pipeline
```

The Controller schedules jobs while Agents execute them independently.

---

## 🚀 Why Distributed Jenkins?

A company may have:

- 500+ Developers
- Hundreds of repositories
- Thousands of builds every day

Running everything on one machine would create a bottleneck.

Using multiple Agents provides:

- Faster builds
- Better resource utilization
- Parallel execution
- High availability
- Easier scaling

---

## 🌍 Enterprise Workflow

A typical production CI/CD workflow looks like this:

```text
Developer
      │
      ▼
GitHub Repository
      │
Webhook
      ▼
Jenkins Controller
      │
Available Agent
      ▼
Compile
      ▼
Run Tests
      ▼
SonarQube
      ▼
Docker Build
      ▼
Container Registry
      ▼
Kubernetes Cluster
      ▼
Production Deployment
      ▼
Slack / Email Notification
```

This workflow is commonly used in modern DevOps environments.

---

## 🖥️ Types of Jenkins Agents

Different Agents are used for different workloads.

| Agent Type | Typical Use Case |
|------------|------------------|
| Linux Agent | Java, Python, Go Applications |
| Windows Agent | .NET Applications |
| Docker Agent | Containerized Builds |
| Kubernetes Agent | Dynamic & Scalable Pipelines |
| Cloud VM | AWS, Azure, GCP Workloads |

Each Agent can be optimized for its specific technology stack.

---

## 📈 Benefits of Distributed Jenkins

✅ Parallel build execution

✅ Better CPU and memory utilization

✅ Faster CI/CD pipelines

✅ Easy horizontal scaling

✅ Reduced Controller workload

✅ Improved reliability

---

## 💡 Production Best Practices

- Keep the Controller lightweight.
- Execute builds only on Agents.
- Use Labels to assign jobs.
- Monitor Agent health.
- Backup the Controller regularly.
- Remove inactive Agents.
- Use Docker or Kubernetes Agents for dynamic workloads.
- Keep plugins updated.

---

## 💡 Interview Tip

**Question:** Why do enterprises use Distributed Jenkins instead of a single server?

**Answer:**

Distributed Jenkins separates job management from job execution. The Controller manages Jenkins, while multiple Agents execute builds in parallel. This improves scalability, reliability, performance, and resource utilization, making it suitable for large CI/CD environments.

---

## 📝 Key Takeaways

- Enterprise Jenkins uses one Controller and multiple Agents.
- Agents execute builds in parallel.
- Different Agents support different technologies.
- Distributed Jenkins improves scalability and performance.
- This is the recommended architecture for production environments.

---

# 📝 Summary

- Jenkins follows a Controller-Agent Architecture.
- The Controller manages Jenkins and schedules jobs.
- Agents execute build, test, and deployment tasks.
- Executors enable parallel build execution.
- Workspaces store build files during execution.
- The Build Queue manages waiting jobs.
- Distributed Jenkins is the recommended production architecture.

---

📚 **Next Topic:** Jenkins Pipelines & Jenkinsfile
