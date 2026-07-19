# Jenkins Pipelines — Cheat Sheet

## Pipeline Skeleton (Declarative)

```groovy
pipeline {
    agent any
    environment { }
    parameters { }
    triggers { }
    options { }
    stages {
        stage('Name') {
            when { }
            steps { }
            post { }
        }
    }
    post {
        always { }
        success { }
        failure { }
        unstable { }
        changed { }
    }
}
```

## Agent Types

| Agent | Use Case |
|---|---|
| `agent any` | Run on any available executor |
| `agent none` | No global agent; each stage defines its own |
| `agent { label 'linux' }` | Run on nodes with a specific label |
| `agent { docker 'node:18' }` | Run inside a Docker container |
| `agent { kubernetes { ... } }` | Run as a Kubernetes pod (EKS agents) |

## `when` Conditions

| Condition | Example |
|---|---|
| Branch | `when { branch 'main' }` |
| Environment var | `when { environment name: 'ENV', value: 'prod' }` |
| Expression | `when { expression { params.RUN_TESTS } }` |
| Not | `when { not { branch 'main' } }` |
| All of | `when { allOf { branch 'main'; environment name: 'ENV', value: 'prod' } }` |

## Common Steps

| Step | Purpose |
|---|---|
| `sh 'cmd'` | Run a shell command |
| `checkout scm` | Checkout the configured SCM |
| `echo 'msg'` | Print a message |
| `input message: '...'` | Manual approval gate |
| `retry(n) { }` | Retry a block n times |
| `timeout(time: n, unit: 'MINUTES') { }` | Fail block after timeout |
| `parallel { }` | Run stages/blocks concurrently |
| `withCredentials([...]) { }` | Inject secrets scoped to a block |
| `archiveArtifacts artifacts: '*.jar'` | Save build artifacts |
| `junit 'results/*.xml'` | Publish test results |
| `stash` / `unstash` | Pass files between stages/agents |
| `cleanWs()` | Clean the workspace |

## Credentials Binding Types

| Type | Use Case |
|---|---|
| `usernamePassword` | Username + password pair |
| `string` | Secret text / API token |
| `sshUserPrivateKey` | SSH key auth |
| `AmazonWebServicesCredentialsBinding` | AWS access key/secret |
| `file` | Secret file (e.g., kubeconfig) |

## Environment Variables (Built-in)

| Variable | Meaning |
|---|---|
| `BUILD_NUMBER` | Current build number |
| `BUILD_ID` | Same as build number (legacy) |
| `JOB_NAME` | Name of the job |
| `WORKSPACE` | Path to the workspace |
| `GIT_COMMIT` | Full commit SHA |
| `BRANCH_NAME` | Current branch (multibranch pipelines) |
| `BUILD_URL` | Link to the build |

## Declarative vs Scripted Quick Reference

| Need | Declarative | Scripted |
|---|---|---|
| Simple linear CI/CD | ✅ Best fit | Overkill |
| Dynamic stage generation | ⚠️ Awkward | ✅ Natural |
| Built-in linting (`declarative-linter`) | ✅ | ❌ |
| Full Groovy control flow | ⚠️ Only inside `script {}` | ✅ Native |

## Shared Library Import

```groovy
@Library('my-shared-library') _
// or pinned to a branch/tag:
@Library('my-shared-library@v1.2.0') _
```

## Useful CLI / Groovy Console Snippets

```groovy
// Cancel all queued builds for a job
Jenkins.instance.getItemByFullName('my-job').getBuilds().each { it.doStop() }
```

```bash
# Trigger a build remotely via CLI
java -jar jenkins-cli.jar -s http://jenkins-url/ build my-job -p ENV=prod
```
