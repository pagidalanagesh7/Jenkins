# Jenkins Day-05 Cheat Sheet

## Git Commands

Clone Repository

```bash
git clone <repository-url>
```

Check Repository Status

```bash
git status
```

Add Files

```bash
git add .
```

Commit Changes

```bash
git commit -m "Commit Message"
```

Push Code

```bash
git push origin main
```

Pull Latest Code

```bash
git pull origin main
```

---

# GitHub Integration

Repository URL

```text
https://github.com/<username>/<repository>.git
```

Branch

```text
main
```

Authentication Methods

- Personal Access Token (PAT)
- SSH Key

Recommended Authentication

✅ SSH Key (Production)
✅ PAT (Small Projects & Beginners)

---

# Jenkins Git Plugin

Install Plugin

```text
Manage Jenkins

↓

Plugins

↓

Available Plugins

↓

Git Plugin

↓

Install
```

Configure Git

```text
Manage Jenkins

↓

Tools

↓

Git Installations
```

Verify Git

```bash
git --version
```

---

# Jenkins Credentials

Navigate to

```text
Manage Jenkins

↓

Credentials

↓

System

↓

Global Credentials

↓

Add Credentials
```

Supported Types

- Username & Password
- Secret Text (PAT)
- SSH Username with Private Key

---

# Source Code Management (SCM)

Pipeline

↓

Pipeline Script from SCM

↓

SCM

↓

Git

Required Fields

- Repository URL
- Branch
- Credentials

---

# Build Triggers

Manual Build

```text
Build Now
```

Build Periodically

```text
0 22 * * *
```

Poll SCM

```text
H/5 * * * *
```

GitHub Webhook

```text
Push Event → Jenkins Build
```

---

# Poll SCM vs Webhook

| Poll SCM | GitHub Webhook |
|----------|----------------|
| Checks repository repeatedly | GitHub notifies Jenkins |
| Delayed builds | Instant builds |
| More server load | Less server load |
| Good for testing | Recommended for Production |

---

# Jenkinsfile Structure

```groovy
pipeline {

    agent any

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo "Build"
            }
        }

        stage('Test') {
            steps {
                echo "Test"
            }
        }

        stage('Deploy') {
            steps {
                echo "Deploy"
            }
        }
    }
}
```

---

# CI/CD Flow

```text
Developer

↓

Git Commit

↓

Git Push

↓

GitHub

↓

Webhook

↓

Jenkins

↓

Checkout

↓

Build

↓

Test

↓

Deploy
```

---

# Production Best Practices

- Store Jenkinsfile in Git.
- Use SSH Keys or PAT.
- Enable GitHub Webhooks.
- Never hardcode credentials.
- Keep plugins updated.
- Protect the main branch.
- Rotate credentials regularly.
- Use Pipeline as Code.
- Review Jenkins logs regularly.

---

# Useful Resources

## Official Documentation

- Jenkins Documentation
  https://www.jenkins.io/doc/

- Jenkins Pipeline Syntax
  https://www.jenkins.io/doc/book/pipeline/

- Git Plugin Documentation
  https://plugins.jenkins.io/git/

- Git Documentation
  https://git-scm.com/doc

- GitHub Documentation
  https://docs.github.com/

---

## Practice Repositories

Spring PetClinic

https://github.com/spring-projects/spring-petclinic

Node.js Sample App

https://github.com/heroku/node-js-sample

Python Flask Sample

https://github.com/pallets/flask

Java Maven Sample

https://github.com/jenkins-docs/simple-java-maven-app

---

## Learning Resources

Jenkins Official YouTube

https://www.youtube.com/@JenkinsCI

GitHub Skills

https://skills.github.com/

Learn Git Branching

https://learngitbranching.js.org/

Roadmap.sh DevOps

https://roadmap.sh/devops
