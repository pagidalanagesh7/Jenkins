# 📖 Theory

# 🔐 Jenkins Credentials & Secrets Management

## What are Jenkins Credentials?

Jenkins Credentials are a secure way to store sensitive information such as passwords, API tokens, SSH keys, and secret files. Instead of hardcoding secrets inside a Jenkinsfile, Jenkins encrypts and manages them securely.

> **Example:** Store your Docker Hub password as a Jenkins Credential instead of writing it directly in the pipeline.

---

# Why are Credentials Required?

Hardcoding secrets in a Jenkinsfile is a security risk because anyone with repository access can view them.

Using Jenkins Credentials provides:

* Secure storage
* Encrypted secrets
* Masked values in console logs
* Easy management and updates

---

# Types of Jenkins Credentials

### 1. Secret Text

Stores a single secret value.

**Used for:**

* GitHub Personal Access Token (PAT)
* Kubernetes Token
* SonarQube Token

---

### 2. Username with Password

Stores both username and password.

**Used for:**

* Docker Hub
* Nexus Repository
* Git Authentication

---

### 3. SSH Username with Private Key

Stores an SSH private key for secure authentication.

**Used for:**

* Git over SSH
* Remote Linux servers
* Deployment automation

---

### 4. Secret File

Stores an entire file securely.

**Used for:**

* kubeconfig
* SSL Certificates
* Service Account JSON

---

# Jenkins Credentials Store

Credentials are managed from:

**Manage Jenkins → Credentials → System → Global Credentials**

You can also create Folder-level credentials to restrict access to specific projects.

---

# Adding Credentials

Steps:

1. Open **Manage Jenkins**
2. Click **Credentials**
3. Select **System**
4. Open **Global Credentials**
5. Click **Add Credentials**
6. Choose the required credential type
7. Save

---

# Using Credentials in Jenkinsfile

Example:

```groovy
environment {
    GITHUB_TOKEN = credentials('github-token')
}
```

Jenkins automatically injects the secret into the environment during pipeline execution.

---

# Using `withCredentials()`

Example:

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

The credentials are available only inside the `withCredentials()` block, making it a secure way to handle secrets.

---

# Docker Authentication Example

```groovy
withCredentials([
    usernamePassword(
        credentialsId: 'dockerhub-creds',
        usernameVariable: 'USER',
        passwordVariable: 'PASS'
    )
]) {
    sh '''
    echo $PASS | docker login -u $USER --password-stdin
    '''
}
```

This securely logs in to Docker Hub without exposing the password.

---

# Git Authentication using SSH

```groovy
git credentialsId: 'github-ssh',
url: 'git@github.com:company/app.git'
```

Jenkins uses the stored SSH key to clone the repository securely.

---

# Kubernetes Authentication

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

The kubeconfig file is injected temporarily and removed after the pipeline finishes.

---

# Environment Variables vs Credentials

| Environment Variables  | Jenkins Credentials  |
| ---------------------- | -------------------- |
| Plain text             | Encrypted            |
| Less secure            | More secure          |
| Visible in logs        | Automatically masked |
| Used for configuration | Used for secrets     |

---

# Best Practices

* Never hardcode passwords.
* Use `withCredentials()` whenever possible.
* Rotate secrets regularly.
* Follow the principle of least privilege.
* Prefer SSH keys or API tokens over passwords.

---

# Common Mistakes

* Hardcoding secrets in Jenkinsfiles.
* Printing passwords in console logs.
* Using one credential for every environment.
* Storing secrets in Git repositories.

---

# Summary

Jenkins Credentials provide a secure and centralized way to manage sensitive information in CI/CD pipelines. By using encrypted credentials, you can safely authenticate with Git, Docker, Kubernetes, and other services while following enterprise security best practices.
