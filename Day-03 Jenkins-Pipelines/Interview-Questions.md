# Jenkins Pipelines — Scenario-Based Interview Questions

## 1. Your Jenkins pipeline works fine on `main` but fails on feature branches with "workspace not found." What's happening?

Likely a multibranch pipeline where the `Jenkinsfile` references a shared library or agent label that only exists in certain configurations, or the workspace was cleaned up (`cleanWs()`) in a prior stage and a later stage assumes files still exist. Check whether `agent` is defined per-stage with `agent none` at the top — each stage then gets a *fresh* workspace unless you explicitly `stash`/`unstash` files between stages.

## 2. A production deployment stage ran even though the build tests failed. How do you prevent this?

This usually means `post { failure { } }` wasn't used to halt downstream stages, or the pipeline lacked `when { expression { currentBuild.result != 'FAILURE' } }` guards. In Declarative pipelines, a failed `steps` block in one stage should stop the pipeline by default — so investigate whether tests were wrapped in a `try/catch` that swallowed the failure without setting `currentBuild.result = 'FAILURE'` or re-throwing.

## 3. You need 40 microservice repos to share the same build/test/deploy logic without copy-pasting Jenkinsfiles. How do you approach it?

Build a **Shared Library** repo with reusable steps in `vars/*.groovy` (e.g., `buildAndDeploy.groovy`), version it with Git tags, and have each microservice's Jenkinsfile just call `@Library('shared-lib@v2') _` followed by a one-line call like `buildAndDeploy('service-name', 'staging')`. This centralizes logic — a fix in the library propagates everywhere on next build.

## 4. Your pipeline needs AWS credentials to push to ECR, but you don't want them exposed in logs or hardcoded. How do you handle this securely?

Use the Jenkins Credentials plugin with `AmazonWebServicesCredentialsBinding`, scoped inside `withCredentials([...])` only for the stage that needs it — never as a global environment variable. Also enable `maskPasswords` or ensure the plugin masks secret values in console output, and avoid `echo`-ing any credential-derived variables.

## 5. A pipeline stage occasionally fails due to a flaky network call to an external API. How do you make it resilient without masking real failures?

Wrap the specific step in `retry(n) { timeout(time: X, unit: 'MINUTES') { ... } }` rather than retrying the entire pipeline. This limits blast radius — you're only retrying the flaky call, and the timeout prevents an indefinite hang if the retry itself gets stuck.

## 6. Your team wants manual approval before deploying to prod, but only for the `main` branch, and it should auto-timeout after 15 minutes if nobody responds. How do you build this?

```groovy
stage('Approve Prod Deploy') {
    when { branch 'main' }
    steps {
        timeout(time: 15, unit: 'MINUTES') {
            input message: 'Deploy to production?', ok: 'Deploy'
        }
    }
}
```
If the timeout fires before approval, the pipeline aborts that stage (and typically the pipeline) rather than hanging indefinitely.

## 7. How would you design a Jenkins pipeline to build and deploy Docker images to Amazon EKS, keeping build agents ephemeral and cost-efficient?

Use the Kubernetes plugin to run Jenkins agents as short-lived pods inside the EKS cluster itself (`agent { kubernetes { ... } }`). Each pipeline run spins up a pod with the required containers (e.g., `docker`/`kaniko`, `kubectl`, `aws-cli`), and the pod is torn down after the build completes — so you're not paying for idle EC2-based agents, and scaling is handled natively by the Kubernetes plugin's pod templates.

## 8. Two teams keep colliding — one deploys a hotfix while another's regular pipeline run is mid-deploy to the same environment. How do you prevent overlapping deployments?

Add `options { disableConcurrentBuilds() }` to serialize builds of the same job, and/or use the `Lockable Resources` plugin to lock a shared resource (e.g., the `prod-environment` lock) across *different* jobs so only one deployment to that environment can proceed at a time, queuing the rest.

## 9. Your Jenkinsfile has grown to 300+ lines with deeply nested conditionals. How do you refactor it for maintainability?

Move reusable logic into a Shared Library (`vars/` for pipeline-callable steps, `src/` for Groovy classes/utilities), replace repeated `sh` blocks with parameterized library functions, and keep the Jenkinsfile itself as a thin orchestration layer — mostly `stage` names calling library functions. This also lets you unit-test library logic outside of Jenkins.

## 10. How do you debug a pipeline that's stuck at "Waiting for next available executor" indefinitely?

Check whether the requested `agent` label actually has any online, connected nodes matching it (`Manage Jenkins > Nodes`), whether `disableConcurrentBuilds()` combined with a long-running prior build is blocking the executor, or whether the Kubernetes plugin failed to provision a pod (check pod events/logs in the cluster — often an image pull failure or resource quota issue on the namespace).
