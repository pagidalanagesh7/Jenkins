# 🚀 Jenkins Learning Series – Day 3 (Part 2)

# Production Jenkinsfile Deep Dive

> **Real Enterprise Pipeline • Docker • Credentials • Parallel Builds • Production Ready**

---

# 📖 Introduction

In Part 1, we learned the fundamentals of Jenkins Pipelines.

In this part, we'll explore how enterprise organizations build **production-ready Jenkins Pipelines** that automate the entire software delivery lifecycle—from code checkout to deployment.

You'll also learn about environment variables, credentials management, post actions, parallel builds, and production best practices.

---

# 🏗 Production Pipeline Architecture

```
Developer
      │
      ▼
 GitHub Repository
      │
      ▼
 GitHub Webhook
      │
      ▼
 Jenkins Pipeline
      │
      ▼
 Checkout Source Code
      │
      ▼
 Build Application
      │
      ▼
 Run Unit Tests
      │
      ▼
 Static Code Analysis
      │
      ▼
 Build Docker Image
      │
      ▼
 Push Docker Image
      │
      ▼
 Deploy to Kubernetes
      │
      ▼
 Slack / Email Notification
```

---

# 📄 Production Declarative Jenkinsfile

```groovy
pipeline {

    agent any

    environment {

        APP_NAME = "springboot-app"
        DOCKER_IMAGE = "company/springboot-app"
        VERSION = "1.0.${BUILD_NUMBER}"

    }

    stages {

        stage('Checkout') {

            steps {

                git 'https://github.com/company/project.git'

            }

        }

        stage('Build') {

            steps {

                sh 'mvn clean package'

            }

        }

        stage('Test') {

            steps {

                sh 'mvn test'

            }

        }

        stage('Docker Build') {

            steps {

                sh 'docker build -t ${DOCKER_IMAGE}:${VERSION} .'

            }

        }

        stage('Deploy') {

            steps {

                echo 'Deploying Application...'

            }

        }

    }

    post {

        success {

            echo 'Deployment Successful'

        }

        failure {

            echo 'Deployment Failed'

        }

        always {

            cleanWs()

        }

    }

}
```

---

# 🧩 Understanding Pipeline Blocks

## pipeline

The root block that defines the entire CI/CD pipeline.

---

## agent

Specifies where the pipeline runs.

```groovy
agent any
```

Jenkins automatically selects an available agent.

---

## environment

Defines variables that can be used throughout the pipeline.

Example:

```groovy
environment {

    APP_NAME = "springboot-app"

}
```

Benefits:

- Reusable values
- Cleaner Jenkinsfiles
- Easy maintenance

---

## stages

Contains all pipeline stages.

Each stage performs one logical task.

Examples:

- Checkout
- Build
- Test
- Package
- Deploy

---

## steps

Contains commands executed inside a stage.

Example:

```groovy
steps {

    sh 'mvn clean package'

}
```

---

## post

Defines actions executed after pipeline completion.

Example:

```groovy
post {

    success {

        echo 'Success'

    }

    failure {

        echo 'Failed'

    }

}
```

---

# 🌍 Pipeline Execution Flow

```
Pipeline Started
      │
      ▼
Checkout
      │
      ▼
Build
      │
      ▼
Unit Test
      │
      ▼
Code Analysis
      │
      ▼
Docker Build
      │
      ▼
Docker Push
      │
      ▼
Deploy
      │
      ▼
Notification
      │
      ▼
Pipeline Finished
```

---

# 🌱 Environment Variables

Environment variables make pipelines flexible and reusable.

Example:

```groovy
environment {

    APP_NAME = "springboot-app"
    VERSION = "1.0.1"

}
```

Common Jenkins Variables:

| Variable | Description |
|-----------|-------------|
| BUILD_NUMBER | Current build number |
| BUILD_ID | Unique build identifier |
| JOB_NAME | Jenkins job name |
| WORKSPACE | Workspace directory |
| NODE_NAME | Agent name |
| JENKINS_HOME | Jenkins home directory |
| BUILD_URL | URL of the current build |
| EXECUTOR_NUMBER | Executor ID |

---

# 🔐 Jenkins Credentials

Sensitive information should **never** be hardcoded inside a Jenkinsfile.

Jenkins provides a secure Credentials Store.

Supported credential types:

- Username & Password
- Secret Text
- Secret File
- SSH Private Key
- Docker Registry Credentials
- GitHub Personal Access Token (PAT)
- AWS Credentials
- Kubernetes Credentials

Example:

```groovy
environment {

    DOCKER_CREDS = credentials('dockerhub-creds')

}
```

Benefits:

- Secure storage
- Encryption
- Easy management
- Reusable across jobs

---

# ⚡ Parallel Builds

Parallel stages execute multiple tasks simultaneously, reducing pipeline execution time.

Example:

```groovy
stage('Tests') {

    parallel {

        stage('Unit Tests') {

            steps {

                sh 'mvn test'

            }

        }

        stage('API Tests') {

            steps {

                sh './run-api-tests.sh'

            }

        }

    }

}
```

Execution Flow:

```
Build
   │
   ▼
-----------------------------
│            │             │
▼            ▼             ▼
Unit Test  API Test   UI Test
│            │             │
---------------Merge---------
               │
               ▼
             Deploy
```

---

# 🐳 Docker Integration

Jenkins integrates seamlessly with Docker.

Typical workflow:

```
Checkout Code
      │
      ▼
Compile
      │
      ▼
Run Tests
      │
      ▼
Build Docker Image
      │
      ▼
Push to Docker Registry
      │
      ▼
Deploy
```

Docker Commands:

```bash
docker build -t company/app:v1 .
docker push company/app:v1
```

---

# ☸ Kubernetes Deployment

After building the Docker image, Jenkins can deploy applications to Kubernetes.

Example:

```bash
kubectl apply -f deployment.yaml
kubectl apply -f service.yaml
```

Production Flow:

```
Jenkins

     │

Docker Build

     │

Docker Registry

     │

Kubernetes Cluster

     │

Pods Updated

     │

Application Available
```

---

# 📋 Sample Jenkins Console Output

```text
Started by GitHub push

Checking out Repository

Running Maven Build...

BUILD SUCCESS

Running Unit Tests...

Tests Passed

Building Docker Image...

Successfully tagged company/app:1.0.15

Pushing Docker Image...

Image Pushed Successfully

Deploying to Kubernetes...

deployment.apps/app configured

service/app unchanged

Deployment Successful

Finished: SUCCESS
```

---

# ❌ Sample Failure Output

```text
Started by GitHub push

Checking out Repository

Running Maven Build...

BUILD FAILURE

Tests Failed

Pipeline Aborted

Finished: FAILURE
```

---

# 📂 Typical Production Project Structure

```
project/

├── src/
├── pom.xml
├── Dockerfile
├── Jenkinsfile
├── README.md
├── k8s/
│   ├── deployment.yaml
│   ├── service.yaml
│   └── ingress.yaml
└── scripts/
    ├── build.sh
    └── deploy.sh
```

---

# 💼 Enterprise CI/CD Workflow

```
Developer

      │

Git Push

      │

GitHub

      │

Webhook

      │

Jenkins

      │

Checkout

      │

Build

      │

Unit Tests

      │

SonarQube Scan

      │

Docker Build

      │

Push Docker Image

      │

Deploy to Dev

      │

Approval

      │

Deploy to Production

      │

Slack Notification
```

---

# ⭐ Production Best Practices

- Store Jenkinsfile in the Git repository.
- Never hardcode passwords or tokens.
- Use Jenkins Credentials for secrets.
- Keep each stage focused on one responsibility.
- Use environment variables for reusable values.
- Enable parallel execution whenever possible.
- Use meaningful stage names.
- Fail fast when errors occur.
- Clean the workspace after every build.
- Archive important build artifacts.
- Add notifications for success and failure.
- Keep pipelines modular and reusable.

---

# ⚠ Common Mistakes

- Hardcoding secrets
- Using one large stage for everything
- Ignoring failed tests
- Not cleaning the workspace
- Skipping code quality checks
- Not versioning Jenkinsfiles
- Running everything sequentially
- Missing post-build notifications

---

# 🎯 Interview Questions

1. What is a Jenkinsfile?

2. Explain the purpose of the `agent` block.

3. What is the `environment` block used for?

4. What are Jenkins Credentials?

5. Why should credentials never be hardcoded?

6. What is the purpose of the `post` block?

7. Difference between `success`, `failure`, and `always` in post actions?

8. What are parallel stages?

9. How does Jenkins integrate with Docker?

10. How does Jenkins deploy applications to Kubernetes?

11. How do you reduce pipeline execution time?

12. What are the best practices for writing production Jenkins Pipelines?

---

# 📝 Summary

In this part, you learned:

- Production Declarative Jenkinsfile
- Pipeline Blocks
- Environment Variables
- Jenkins Credentials
- Parallel Builds
- Docker Integration
- Kubernetes Deployment
- Sample Console Outputs
- Enterprise CI/CD Workflow
- Production Best Practices
- Common Mistakes
- Interview Questions

---

# 🚀 What's Next?

## **Day 4 – Jenkins Credentials & Secrets Management**

Topics:

- Jenkins Credentials Store
- Secret Text
- Username & Password
- SSH Keys
- Docker Registry Credentials
- GitHub PAT
- AWS Credentials
- Kubernetes Secrets
- Secure Pipeline Examples
- Production Security Best Practices

Happy Learning! 🎉