# 🎯 Jenkins Learning Series – Day 2

# Jenkins Architecture Interview Questions

This document contains frequently asked **Jenkins Architecture** interview questions, ranging from beginner to intermediate level. These questions are commonly asked in DevOps, CI/CD, and Cloud Engineer interviews.

---

# 1. What is Jenkins Architecture?

**Answer:**

Jenkins Architecture is based on a **Controller-Agent model**. The Controller manages Jenkins, while Agents execute build jobs. This architecture enables distributed and parallel builds, making Jenkins suitable for enterprise CI/CD environments.

---

# 2. What is the role of the Jenkins Controller?

**Answer:**

The Jenkins Controller is responsible for:

- Managing Jenkins
- Scheduling build jobs
- Storing configurations
- Managing plugins
- Maintaining build history
- Assigning jobs to Agents
- Managing credentials

---

# 3. What is a Jenkins Agent?

**Answer:**

A Jenkins Agent is a machine that executes build jobs assigned by the Controller. Agents can be Linux servers, Windows servers, Docker containers, Kubernetes Pods, or cloud virtual machines.

---

# 4. Why are Jenkins Agents required?

**Answer:**

Agents distribute the build workload across multiple machines, improving:

- Performance
- Scalability
- Parallel execution
- Resource utilization

---

# 5. What is an Executor in Jenkins?

**Answer:**

An Executor is a worker process inside an Agent.

**One Executor can execute one build at a time.**

More Executors allow multiple builds to run in parallel.

---

# 6. What is Workspace?

**Answer:**

Workspace is the directory where Jenkins checks out source code, compiles the application, runs tests, and generates build artifacts.

---

# 7. What is the Build Queue?

**Answer:**

The Build Queue stores jobs waiting for an available Executor. Once an Executor becomes free, Jenkins automatically starts the waiting build.

---

# 8. Why shouldn't production builds run on the Controller?

**Answer:**

Running builds on the Controller consumes CPU, memory, and disk resources, which can affect Jenkins performance. Production environments execute builds on dedicated Agents.

---

# 9. What is Distributed Jenkins?

**Answer:**

Distributed Jenkins is a setup where one Controller manages multiple Agents. This architecture enables faster builds, better scalability, and improved reliability.

---

# 10. What are Labels in Jenkins?

**Answer:**

Labels are used to assign jobs to specific Agents.

Example:

- linux
- windows
- docker
- kubernetes

A pipeline can target an Agent using:

```groovy
agent {
    label 'linux'
}
```

---

# 11. What happens after a developer pushes code to GitHub?

**Answer:**

1. GitHub sends a webhook.
2. Jenkins Controller receives the request.
3. The Controller creates a build.
4. An available Agent is selected.
5. Source code is checked out.
6. The application is built and tested.
7. Artifacts are generated.
8. The application is deployed.
9. Notifications are sent.

---

# 12. Explain Jenkins Build Flow.

**Answer:**

```
Developer
      │
      ▼
GitHub
      │
Webhook
      ▼
Controller
      ▼
Agent
      ▼
Build
      ▼
Test
      ▼
Package
      ▼
Deploy
      ▼
Notification
```

---

# 13. Difference Between Controller and Agent

| Controller | Agent |
|------------|-------|
| Manages Jenkins | Executes Builds |
| Schedules Jobs | Runs Pipelines |
| Stores Credentials | Uses Credentials |
| Maintains Queue | Executes Assigned Tasks |

---

# 14. Difference Between Workspace and Build Queue

| Workspace | Build Queue |
|-----------|-------------|
| Working Directory | Waiting Area |
| Stores Project Files | Stores Pending Jobs |
| Created During Build | Used Before Build Starts |

---

# 15. Scenario-Based Question

**Question:**

Your Jenkins jobs are waiting and not starting. What will you check?

**Answer:**

- Check Agent availability.
- Verify Executors are free.
- Check Build Queue.
- Confirm Agent is online.
- Review Jenkins logs.
- Check CPU and memory usage.
- Verify Labels match the pipeline configuration.

---

# 🚀 Quick Revision

✔ Controller manages Jenkins.

✔ Agents execute builds.

✔ Executors run builds.

✔ Workspace stores build files.

✔ Build Queue holds waiting jobs.

✔ Distributed Jenkins is recommended for production.