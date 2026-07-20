# 📄 Jenkins Credentials Cheat Sheet

# 🔐 Jenkins Credentials & Secrets Management

## Credential Types

| Type                          | Used For                                |
| ----------------------------- | --------------------------------------- |
| Secret Text                   | API Tokens, GitHub PAT, SonarQube Token |
| Username & Password           | Docker Hub, Nexus, Git Login            |
| SSH Username with Private Key | Git over SSH, Linux Servers             |
| Secret File                   | kubeconfig, SSL Certificates, JSON Keys |

---

## Credentials Location

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

---

## Using `credentials()`

```groovy
environment {
    GITHUB_TOKEN = credentials('github-token')
}
```

---

## Using `withCredentials()`

```groovy
withCredentials([
    usernamePassword(
        credentialsId: 'dockerhub-creds',
        usernameVariable: 'USER',
        passwordVariable: 'PASS'
    )
]) {
    sh 'docker login'
}
```

---

## Git Authentication

```groovy
git credentialsId: 'github-ssh',
url: 'git@github.com:company/app.git'
```

---

## Kubernetes Authentication

```groovy
withCredentials([
    file(
        credentialsId: 'kubeconfig',
        variable: 'KUBECONFIG'
    )
]) {
    sh 'kubectl get nodes'
}
```

---

## Best Practices ✅

* Never hardcode secrets.
* Use `withCredentials()` for sensitive data.
* Rotate credentials regularly.
* Use least-privilege access.
* Prefer SSH keys or API tokens over passwords.
* Store secrets only in Jenkins Credentials.

---

## Common Mistakes ❌

* Hardcoding passwords in Jenkinsfiles.
* Printing secrets in logs.
* Committing secrets to Git.
* Sharing one credential across all environments.
* Using admin credentials unnecessarily.

---

## Quick Revision

* 🔐 Credentials are encrypted.
* 🔑 Store passwords, tokens, SSH keys, and secret files securely.
* 🚀 Jenkins injects credentials only during pipeline execution.
* 🛡️ `withCredentials()` is the recommended way to handle secrets.
* 📦 Use Folder Credentials for better project isolation.

---

## One-Line Interview Answer

> **Jenkins Credentials provide a secure, encrypted way to store and use sensitive information like passwords, API tokens, SSH keys, and secret files inside CI/CD pipelines without exposing them in code or logs.**
