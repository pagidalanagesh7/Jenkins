# Jenkins Shared Libraries

## 📖 Introduction

As Jenkins projects grow, managing multiple Jenkinsfiles across different repositories becomes difficult. The same pipeline stages such as **Build**, **Test**, **Docker Build**, and **Deploy** are often copied into every project. This leads to duplicate code, inconsistent pipelines, and increased maintenance effort.

**Jenkins Shared Libraries** solve this problem by allowing you to write reusable pipeline code once and use it across multiple Jenkins pipelines. Instead of duplicating the same logic in every Jenkinsfile, you can centralize common functions in a Shared Library and call them whenever needed.

Shared Libraries are written using **Groovy** and are stored in a separate Git repository. Jenkins loads these libraries during pipeline execution, making your Jenkinsfiles smaller, cleaner, and easier to maintain.

Today, almost every enterprise using Jenkins relies on Shared Libraries to standardize CI/CD pipelines, improve collaboration between teams, and simplify pipeline maintenance.

---

# 🎯 What are Jenkins Shared Libraries?

A **Jenkins Shared Library** is a collection of reusable Groovy scripts, pipeline functions, and utility classes that can be shared across multiple Jenkins pipelines.

Instead of writing the same pipeline logic repeatedly in every Jenkinsfile, you define the common functionality once inside a Shared Library and reuse it wherever required.

A Shared Library acts as a **central repository** for pipeline code. Jenkins loads this library whenever a pipeline starts, making reusable functions available throughout the pipeline.

### Common reusable tasks include:

- Source Code Checkout
- Build Automation
- Unit Testing
- Docker Image Build
- Security Scanning
- Deployments
- Slack Notifications
- Email Notifications
- Rollback Tasks

This approach makes CI/CD pipelines more maintainable, scalable, and consistent.

---

# 🎯 Why Do We Need Shared Libraries?

Imagine an organization with **100 applications**.

Without Shared Libraries, every repository contains its own Jenkinsfile.

```text
Repository A
├── Jenkinsfile

Repository B
├── Jenkinsfile

Repository C
├── Jenkinsfile

Repository D
├── Jenkinsfile
```

Each Jenkinsfile contains almost identical stages:

- Source Code Checkout
- Build
- Unit Testing
- Docker Image Build
- Security Scan
- Push Image
- Deploy to Kubernetes
- Notifications

Whenever the deployment process changes, every Jenkinsfile must be updated manually.

This approach creates several problems:

- Duplicate pipeline code
- Difficult maintenance
- Inconsistent CI/CD pipelines
- Higher chances of human error
- More development effort

---

With Shared Libraries:

```text
          Shared Library
                │
     ┌──────────┼──────────┐
     │          │          │
Project A   Project B   Project C
     │          │          │
 Jenkinsfile Jenkinsfile Jenkinsfile
```

Each Jenkinsfile simply calls reusable functions from the Shared Library.

This creates a **single source of truth** for all pipeline logic.

### Benefits

- Code Reusability
- Cleaner Jenkinsfiles
- Faster Development
- Easy Maintenance
- Standardized Pipelines
- Better Collaboration
- Enterprise Scalability

> 💡 **Key Takeaway:** Write pipeline logic once and reuse it everywhere.

---

# 🏗️ How Jenkins Shared Libraries Work

A Jenkins Shared Library stores reusable pipeline logic in a separate Git repository.

Whenever Jenkins executes a pipeline, it first loads the Shared Library and then executes the required reusable functions.

This keeps Jenkinsfiles small while the actual implementation resides inside the library.

---

## Workflow

```text
Developer
    │
Git Push
    │
GitHub
    │
Jenkins Pipeline
    │
Load Shared Library
    │
Reusable Functions
    │
Build
    │
Test
    │
Docker Build
    │
Deploy
    │
Notification
```

### Step 1 – Developer Pushes Code

The developer pushes application code to GitHub.

### Step 2 – Jenkins Pipeline Starts

Jenkins detects the latest commit and begins executing the Jenkinsfile.

### Step 3 – Shared Library is Loaded

Jenkins imports the configured Shared Library.

Example:

```groovy
@Library('company-library') _
```

### Step 4 – Execute Reusable Functions

Instead of writing hundreds of lines of pipeline code, the Jenkinsfile simply calls reusable functions.

Example:

```groovy
buildApp()

runTests()

buildDockerImage()

deployApplication()
```

These functions perform all required CI/CD tasks.

### Step 5 – Pipeline Completes

The Shared Library executes:

- Build
- Test
- Docker Build
- Security Scan
- Deployment
- Notifications

The Jenkinsfile remains clean and easy to maintain.

---

## Real Production Example

Imagine a company has **200 microservices**.

Without Shared Libraries:

- Every project has its own Jenkinsfile.
- Code duplication is common.
- Updating pipelines becomes difficult.

With Shared Libraries:

```text
           Shared Library
                 │
      ┌──────────┼──────────┐
      │          │          │
 Service A  Service B  Service C
```

Every project reuses the same pipeline functions.

When a deployment process changes, only the Shared Library is updated.

All projects automatically use the latest implementation.

---

## Advantages

- Reusable pipeline code
- Cleaner Jenkinsfiles
- Easy maintenance
- Consistent CI/CD process
- Faster development
- Better scalability
- Enterprise-ready architecture

> 💡 **Key Takeaway:** Jenkins Shared Libraries separate pipeline logic from Jenkinsfiles, making CI/CD pipelines reusable, scalable, and easier to maintain.