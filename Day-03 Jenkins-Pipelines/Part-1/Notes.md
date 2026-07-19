# 🚀 Jenkins Learning Series – Day 3 (Part 1)

# Jenkins Pipelines Explained

> **Pipeline as Code • CI/CD Workflow • Declarative vs Scripted • Interview Ready**

---

# 📌 What is a Jenkins Pipeline?

A **Jenkins Pipeline** is a collection of automated steps that Jenkins executes to build, test, and deploy an application.

Instead of manually performing tasks like compiling code, running tests, packaging artifacts, and deploying applications, all these activities are written inside a **Jenkinsfile** and executed automatically.

A Pipeline allows teams to define their complete CI/CD process as code, making it repeatable, version-controlled, and easy to maintain.

---

# 🤔 Why Do We Need Pipelines?

Without a pipeline, software delivery is mostly manual.

### Traditional Workflow

```
Developer
    │
    ▼
Commit Code
    │
    ▼
Manual Build
    │
    ▼
Manual Testing
    │
    ▼
Manual Deployment
    │
    ▼
Production
```

### Problems

- Time-consuming
- Human errors
- Inconsistent deployments
- Difficult to reproduce builds
- No version control for deployment steps

---

# ✅ Pipeline-Based Workflow

```
Developer
    │
    ▼
Git Push
    │
    ▼
GitHub Webhook
    │
    ▼
Jenkins Pipeline
    │
    ▼
Checkout Code
    │
    ▼
Build
    │
    ▼
Test
    │
    ▼
Package
    │
    ▼
Docker Build
    │
    ▼
Deploy
    │
    ▼
Success Notification
```

Everything happens automatically after a code push.

---

# 💡 What is Pipeline as Code?

**Pipeline as Code (PaC)** means defining the complete CI/CD workflow inside a text file called a **Jenkinsfile**.

Instead of configuring jobs manually through the Jenkins UI, the pipeline configuration is stored in the application's Git repository.

### Benefits

- Version controlled
- Easy to review
- Easy to maintain
- Reusable
- Easy rollback
- Consistent across environments

---

# 🏗 Jenkins Pipeline Lifecycle

```
Developer

      │

Git Commit

      │

GitHub Webhook

      │

Jenkins Trigger

      │

Checkout Source Code

      │

Compile

      │

Run Tests

      │

Package Application

      │

Deploy

      │

Notifications
```

---

# 📦 Types of Jenkins Pipelines

Jenkins supports two pipeline styles.

## 1. Declarative Pipeline

Declarative Pipeline provides a predefined structure.

It is easy to write, easy to understand, and recommended for most projects.

Example:

```groovy
pipeline {

    agent any

    stages {

        stage('Build') {

            steps {

                echo 'Building Application'

            }

        }

    }

}
```

---

## 2. Scripted Pipeline

Scripted Pipeline uses Groovy programming syntax and offers complete flexibility.

Example:

```groovy
node {

    stage('Build') {

        echo 'Building Application'

    }

}
```

---

# 🔄 Declarative vs Scripted Pipeline

| Feature | Declarative | Scripted |
|----------|-------------|-----------|
| Syntax | Simple | Groovy |
| Learning Curve | Easy | Medium |
| Readability | High | Medium |
| Flexibility | Limited | Very High |
| Recommended | Yes | Advanced Users |
| Maintenance | Easy | Medium |
| Best For | Most Projects | Complex Logic |

---

# 🧩 Jenkins Pipeline Components

## pipeline

The root block that defines the entire pipeline.

---

## agent

Defines where the pipeline executes.

Example

```groovy
agent any
```

This tells Jenkins to execute the pipeline on any available agent.

---

## stages

A collection of multiple stages.

```groovy
stages {

}
```

---

## stage

Represents a logical phase of the pipeline.

Examples

- Checkout
- Build
- Test
- Package
- Deploy

---

## steps

Contains the actual commands executed by Jenkins.

Example

```groovy
steps {

    echo 'Hello'

}
```

---

## echo

Prints a message to the Jenkins console.

Example

```groovy
echo 'Build Started'
```

---

# ✨ Your First Jenkinsfile

```groovy
pipeline {

    agent any

    stages {

        stage('Build') {

            steps {

                echo 'Hello Jenkins Pipeline'

            }

        }

    }

}
```

---

# 🔍 How Jenkins Executes This Pipeline

```
Pipeline Started

↓

Allocate Agent

↓

Create Build Workspace

↓

Execute Build Stage

↓

Print Message

↓

Pipeline Finished

↓

SUCCESS
```

---

# 📄 Sample Jenkins Console Output

```text
Started by user admin

Running on Jenkins in /var/lib/jenkins/workspace/demo

[Pipeline] Start of Pipeline

[Pipeline] stage

[Pipeline] { (Build)

Building Application

Hello Jenkins Pipeline

[Pipeline] }

[Pipeline] End of Pipeline

Finished: SUCCESS
```

---

# 🌍 Real Enterprise Pipeline Flow

```
Developer

      │

Push Code

      │

GitHub

      │

Webhook

      │

Jenkins Pipeline

      │

Checkout

      │

Compile

      │

Unit Tests

      │

Code Quality Scan

      │

Package

      │

Docker Build

      │

Push Image

      │

Deploy

      │

Slack / Email Notification
```

---

# ✅ Advantages of Jenkins Pipelines

- Pipeline as Code
- Fully automated
- Version controlled
- Easy collaboration
- Easy rollback
- Faster releases
- Reusable pipelines
- Better visibility
- Reduced human errors
- Consistent deployments

---

# ⚠ Common Mistakes

- Hardcoding credentials inside Jenkinsfile
- Creating one huge stage instead of multiple stages
- Ignoring failed tests
- Not storing Jenkinsfile in Git
- Mixing build and deployment logic
- Not using version control
- Poor stage naming

---

# 💼 Production Best Practices

- Keep Jenkinsfile inside the project repository.
- Create small and meaningful stages.
- Name stages clearly.
- Fail fast when errors occur.
- Store secrets using Jenkins Credentials.
- Use environment variables instead of hardcoded values.
- Keep pipelines simple and reusable.
- Review Jenkinsfiles through pull requests.

---

# 🎯 Interview Questions

### 1. What is a Jenkins Pipeline?

### 2. What is Pipeline as Code?

### 3. What is a Jenkinsfile?

### 4. Difference between Declarative and Scripted Pipelines?

### 5. What is the purpose of the `agent` block?

### 6. What is a stage?

### 7. What is the purpose of the `steps` block?

### 8. Where should the Jenkinsfile be stored?

### 9. What are the advantages of Pipelines?

### 10. Why are Pipelines preferred over Freestyle Jobs?

---

# 📝 Summary

In this part, you learned:

- What a Jenkins Pipeline is
- Why Pipelines are important
- Pipeline as Code
- Jenkins Pipeline Lifecycle
- Declarative Pipeline
- Scripted Pipeline
- Pipeline Components
- First Jenkinsfile
- Pipeline Execution Flow
- Enterprise CI/CD Workflow
- Best Practices
- Interview Questions

---

## 🚀 Next

**Part 2 – Production Jenkinsfile Deep Dive**

Topics include:

- Production Jenkinsfile
- Environment Variables
- Credentials
- Parallel Builds
- Docker Integration
- Post Actions
- Production Best Practices
- Sample Console Outputs
- Enterprise CI/CD Pipeline