# Part 1 — Jenkins Pipeline Basics & Declarative Syntax

## What Is a Jenkins Pipeline?

A Jenkins Pipeline is a suite of plugins that supports implementing and integrating continuous delivery pipelines into Jenkins. It's defined in code — a `Jenkinsfile` — checked into your repo, so your CI/CD process is version-controlled, reviewable, and reproducible.

## Why Pipeline-as-Code?

- **Version controlled**: Every change to the pipeline is tracked in Git.
- **Code review**: Pipeline changes go through PRs like application code.
- **Reusable**: Shared libraries let you standardize across teams.
- **Durable**: Pipelines survive Jenkins master restarts (checkpointing).
- **Auditable**: You always know what ran and when.

## Declarative vs Scripted Pipeline

| Aspect | Declarative | Scripted |
|---|---|---|
| Syntax | Structured, opinionated | Full Groovy, flexible |
| Learning curve | Easier | Steeper |
| Validation | Built-in linting | Manual |
| Use case | Most CI/CD pipelines | Complex, dynamic logic |
| Top-level block | `pipeline { }` | `node { }` |

## Anatomy of a Declarative Pipeline

```groovy
pipeline {
    agent any

    environment {
        APP_ENV = 'production'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
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
        stage('Deploy') {
            steps {
                sh './deploy.sh'
            }
        }
    }

    post {
        always {
            echo 'Pipeline finished.'
        }
        failure {
            mail to: 'team@example.com', subject: 'Build Failed', body: 'Check Jenkins.'
        }
    }
}
```

## Core Sections Explained

- **`agent`**: Where the pipeline (or stage) runs — `any`, `none`, `label`, `docker`, `kubernetes`.
- **`environment`**: Key-value pairs available to all steps as env vars.
- **`stages` / `stage`**: The logical phases of your pipeline (Checkout, Build, Test, Deploy).
- **`steps`**: The actual commands/actions within a stage.
- **`post`**: Actions that run after the pipeline/stage completes — `always`, `success`, `failure`, `unstable`, `changed`.

## Parameters

```groovy
pipeline {
    agent any
    parameters {
        choice(name: 'ENVIRONMENT', choices: ['dev', 'staging', 'prod'], description: 'Target environment')
        booleanParam(name: 'RUN_TESTS', defaultValue: true, description: 'Run test suite?')
        string(name: 'VERSION', defaultValue: 'latest', description: 'Image tag')
    }
    stages {
        stage('Deploy') {
            steps {
                echo "Deploying version ${params.VERSION} to ${params.ENVIRONMENT}"
            }
        }
    }
}
```

## Triggers

```groovy
pipeline {
    agent any
    triggers {
        cron('H 2 * * *')       // scheduled build
        pollSCM('H/5 * * * *')  // poll Git every ~5 mins
    }
    stages { /* ... */ }
}
```

## Common Gotchas

- Forgetting `agent` at the top level → pipeline fails to allocate a workspace.
- Using shell-specific syntax inside `sh` steps without accounting for the default shell (`sh '#!/bin/bash\n...'` if you need bash-specific features).
- Not quoting `${params.X}` correctly inside `sh` blocks — leads to Groovy vs shell variable confusion.
- Overusing `agent any` at every stage when you actually want a specific label (e.g., a build node with Docker installed).

## AWS Context Note

When Jenkins triggers deployments to AWS (EKS, EC2, ECS), it's common to run the Jenkins agent itself inside AWS — either as an EC2 fleet (via the EC2 Fleet plugin) or as Kubernetes pods inside an EKS cluster (via the Kubernetes plugin), so build agents scale with load and tear down when idle.
