# Part 2 — Scripted Pipelines, Shared Libraries & Advanced Constructs

## Scripted Pipeline Basics

Scripted pipelines are plain Groovy wrapped in a `node { }` block. You get full control flow — loops, conditionals, try/catch — without Declarative's guardrails.

```groovy
node('linux') {
    stage('Checkout') {
        checkout scm
    }
    stage('Build') {
        try {
            sh 'mvn clean package'
        } catch (err) {
            currentBuild.result = 'FAILURE'
            throw err
        }
    }
    stage('Test') {
        sh 'mvn test'
    }
    stage('Deploy') {
        if (env.BRANCH_NAME == 'main') {
            sh './deploy.sh prod'
        } else {
            echo 'Skipping deploy for non-main branch'
        }
    }
}
```

## When to Reach for Scripted

- Dynamic stage generation (e.g., looping over a list of microservices to build).
- Complex conditional logic that Declarative's `when` blocks can't cleanly express.
- Fine-grained error handling with custom retry/backoff logic.

## Parallel Execution

```groovy
pipeline {
    agent any
    stages {
        stage('Parallel Tests') {
            parallel {
                stage('Unit Tests') {
                    steps { sh 'npm run test:unit' }
                }
                stage('Integration Tests') {
                    steps { sh 'npm run test:integration' }
                }
                stage('Lint') {
                    steps { sh 'npm run lint' }
                }
            }
        }
    }
}
```

Parallel stages cut pipeline duration significantly when tasks are independent — useful for microservice builds or multi-module test suites.

## Shared Libraries

Shared Libraries let you extract common pipeline logic into a separate Git repo, then import it across multiple Jenkinsfiles.

**Repo structure:**
```
(shared-library-repo)/
├── vars/
│   └── buildAndDeploy.groovy
├── src/
│   └── org/company/Utils.groovy
└── resources/
```

**`vars/buildAndDeploy.groovy`:**
```groovy
def call(String appName, String env) {
    stage("Build ${appName}") {
        sh "docker build -t ${appName}:${env} ."
    }
    stage("Deploy ${appName}") {
        sh "kubectl set image deployment/${appName} ${appName}=${appName}:${env}"
    }
}
```

**Using it in a Jenkinsfile:**
```groovy
@Library('my-shared-library') _

pipeline {
    agent any
    stages {
        stage('Run') {
            steps {
                script {
                    buildAndDeploy('watsonx-service', 'staging')
                }
            }
        }
    }
}
```

Shared Libraries are the standard way large orgs (like IBM-scale platforms) avoid copy-pasting the same 200 lines of Jenkinsfile across 40 microservice repos.

## `when` Conditions (Declarative)

```groovy
stage('Deploy to Prod') {
    when {
        branch 'main'
        environment name: 'DEPLOY_ENV', value: 'prod'
    }
    steps {
        sh './deploy.sh prod'
    }
}
```

## Input Steps (Manual Approval Gates)

```groovy
stage('Approve Production Deploy') {
    steps {
        input message: 'Deploy to production?', ok: 'Deploy'
    }
}
```

Common in regulated environments where a human sign-off is required between staging and prod.

## Credentials Handling

```groovy
stage('Push to ECR') {
    steps {
        withCredentials([[$class: 'AmazonWebServicesCredentialsBinding', credentialsId: 'aws-ecr-creds']]) {
            sh '''
                aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin <account-id>.dkr.ecr.us-east-1.amazonaws.com
                docker push <account-id>.dkr.ecr.us-east-1.amazonaws.com/my-app:latest
            '''
        }
    }
}
```

Never hardcode AWS keys in the Jenkinsfile — always use the Credentials plugin and inject them scoped to the stage that needs them.

## Retry & Timeout

```groovy
stage('Flaky Integration Test') {
    steps {
        retry(3) {
            timeout(time: 5, unit: 'MINUTES') {
                sh './run-integration-tests.sh'
            }
        }
    }
}
```
