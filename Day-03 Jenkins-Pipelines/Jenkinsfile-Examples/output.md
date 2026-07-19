# 📌 Expected Jenkins Console Output

This document contains sample Jenkins console outputs for both successful and failed pipeline executions. These outputs are for learning purposes and closely resemble what you might see in a real Jenkins environment.

---

# ✅ Successful Pipeline Output

```text
Started by GitHub push

Obtained Jenkinsfile from Git repository

[Pipeline] Start of Pipeline

[Pipeline] node

Running on Jenkins-Agent-01 in /var/lib/jenkins/workspace/springboot-app

[Pipeline] {

[Pipeline] stage

[Pipeline] { (Checkout)

Checking out source code from GitHub...

 > git init
 > git fetch origin
 > git checkout main

Checkout completed successfully.

[Pipeline] }

[Pipeline] stage

[Pipeline] { (Build)

Running Maven build...

$ mvn clean package

BUILD SUCCESS

Total time: 18.423 s

[Pipeline] }

[Pipeline] stage

[Pipeline] { (Unit Test)

Running unit tests...

Tests run: 42

Failures: 0

Errors: 0

Skipped: 0

All tests passed successfully.

[Pipeline] }

[Pipeline] stage

[Pipeline] { (Docker Build)

Building Docker image...

Successfully built 4c91f2e9f6ab

Successfully tagged company/springboot-app:25

Docker image created successfully.

[Pipeline] }

[Pipeline] stage

[Pipeline] { (Docker Push)

Logging in to Docker Registry...

Login Succeeded

Pushing Docker image...

Image pushed successfully.

[Pipeline] }

[Pipeline] stage

[Pipeline] { (Deploy to Kubernetes)

Applying Kubernetes manifests...

deployment.apps/springboot-app configured

service/springboot-app unchanged

Deployment completed successfully.

[Pipeline] }

Cleaning workspace...

Finished: SUCCESS
```

---

# ❌ Failed Pipeline Output

```text
Started by GitHub push

Obtained Jenkinsfile from Git repository

[Pipeline] Start of Pipeline

[Pipeline] node

Running on Jenkins-Agent-01 in /var/lib/jenkins/workspace/springboot-app

[Pipeline] {

[Pipeline] stage

[Pipeline] { (Checkout)

Checking out source code...

Checkout completed successfully.

[Pipeline] }

[Pipeline] stage

[Pipeline] { (Build)

Running Maven build...

BUILD SUCCESS

[Pipeline] }

[Pipeline] stage

[Pipeline] { (Unit Test)

Running unit tests...

----------------------------------------------------
Tests run: 42
Failures: 2
Errors: 1
Skipped: 0
----------------------------------------------------

[ERROR] Failed tests:

UserServiceTest

OrderServiceTest

BUILD FAILURE

[Pipeline] }

ERROR: Pipeline aborted due to failed unit tests.

Skipping remaining stages:

- Docker Build
- Docker Push
- Deploy to Kubernetes

Cleaning workspace...

Finished: FAILURE
```

---

# 📝 Notes

- **`Finished: SUCCESS`** indicates that all pipeline stages completed successfully.
- **`Finished: FAILURE`** indicates that one or more stages failed, causing the pipeline to stop.
- In most production pipelines, subsequent stages (such as Docker Build or Deployment) are skipped if an earlier stage fails.
- Jenkins highlights failed stages in red and successful stages in green in the Pipeline visualization.