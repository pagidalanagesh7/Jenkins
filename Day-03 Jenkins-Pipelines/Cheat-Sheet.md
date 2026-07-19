# 🚀 Jenkins Learning Series – Day 3

# Jenkins Pipelines Cheat Sheet

---

# What is a Pipeline?

A Jenkins Pipeline is a collection of automated steps that build, test, package, and deploy an application.

Pipeline as Code = Jenkinsfile

---

# Declarative Pipeline

Recommended for most projects.

```groovy
pipeline {

    agent any

    stages {

        stage('Build') {

            steps {

                echo 'Building...'

            }

        }

    }

}
```

---

# Scripted Pipeline

Uses Groovy syntax.

```groovy
node {

    stage('Build') {

        echo 'Building...'

    }

}
```

---

# Pipeline Structure

```
pipeline

 ├── agent

 ├── environment

 ├── options

 ├── parameters

 ├── tools

 ├── stages

 │     ├── stage

 │     │      └── steps

 └── post
```

---

# Common Pipeline Blocks

| Block | Purpose |
|---------|----------|
| pipeline | Root block |
| agent | Execution node |
| stages | Collection of stages |
| stage | Logical task |
| steps | Commands to execute |
| environment | Environment variables |
| tools | Configure JDK, Maven, etc. |
| options | Pipeline settings |
| parameters | User inputs |
| when | Conditional execution |
| post | Post-build actions |

---

# Common Stage Names

- Checkout
- Build
- Test
- Package
- Docker Build
- Docker Push
- Deploy
- Cleanup

---

# Frequently Used Steps

```groovy
echo 'Hello'
```

```groovy
sh 'mvn clean package'
```

```groovy
git 'https://github.com/company/project.git'
```

```groovy
checkout scm
```

```groovy
cleanWs()
```

---

# Environment Variables

```groovy
environment {

    APP_NAME = "springboot-app"

}
```

Useful Variables

| Variable | Description |
|-----------|-------------|
| BUILD_NUMBER | Build number |
| BUILD_ID | Build ID |
| JOB_NAME | Job name |
| WORKSPACE | Workspace path |
| BUILD_URL | Build URL |
| JENKINS_HOME | Jenkins installation path |

---

# Credentials

```groovy
environment {

    DOCKER_CREDS = credentials('dockerhub-creds')

}
```

Credential Types

- Username & Password
- Secret Text
- Secret File
- SSH Key
- Docker Registry
- GitHub PAT

---

# Post Actions

```groovy
post {

    success {

        echo 'Success'

    }

    failure {

        echo 'Failure'

    }

    always {

        cleanWs()

    }

}
```

---

# Parallel Stage

```groovy
parallel {

    stage('Unit Test') {

    }

    stage('API Test') {

    }

}
```

---

# Pipeline Flow

```
Git Push

↓

Checkout

↓

Build

↓

Test

↓

Docker Build

↓

Deploy

↓

Notification
```

---

# Best Practices

- Store Jenkinsfile in Git
- Never hardcode passwords
- Use Jenkins Credentials
- Use environment variables
- Keep stages small
- Fail Fast
- Use meaningful stage names
- Use parallel stages
- Clean workspace
- Archive artifacts

---

# Common Mistakes

- Hardcoded secrets
- Single large stage
- Ignoring failed tests
- No notifications
- No cleanup
- No version control

---

# Useful Commands

```bash
mvn clean package
```

```bash
mvn test
```

```bash
docker build -t app:v1 .
```

```bash
docker push app:v1
```

```bash
kubectl apply -f deployment.yaml
```

---

# Enterprise CI/CD Flow

```
Developer

↓

GitHub

↓

Webhook

↓

Jenkins

↓

Build

↓

Test

↓

Docker

↓

Registry

↓

Kubernetes

↓

Notification
```