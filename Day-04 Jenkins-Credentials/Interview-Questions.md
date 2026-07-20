# 💼 Jenkins Credentials - Interview Questions

## 1. What are Jenkins Credentials?

**Answer:**

Jenkins Credentials are a secure way to store sensitive information such as passwords, API tokens, SSH keys, and secret files. They help protect secrets from being exposed in Jenkins Pipelines.

---

## 2. Why should we use Jenkins Credentials?

**Answer:**

* Securely stores secrets
* Encrypts sensitive information
* Prevents hardcoding passwords
* Masks secrets in console logs
* Makes CI/CD pipelines more secure

---

## 3. What are the different types of Jenkins Credentials?

**Answer:**

* Secret Text
* Username with Password
* SSH Username with Private Key
* Secret File

---

## 4. What is `withCredentials()` in Jenkins?

**Answer:**

`withCredentials()` securely injects credentials into a pipeline for a specific block of code. The credentials are available only during execution and are removed afterward.

---

## 5. What is `credentials()` in Jenkinsfile?

**Answer:**

The `credentials()` function loads a stored Jenkins Credential into an environment variable, making it available during pipeline execution.

---

## 6. Where are Jenkins Credentials stored?

**Answer:**

They are stored securely in:

**Manage Jenkins → Credentials → System → Global Credentials**

They can also be stored at the Folder level for better access control.

---

## 7. How do you authenticate Docker Hub from Jenkins?

**Answer:**

Store the Docker Hub username and password as Jenkins Credentials and use `withCredentials()` to perform a secure `docker login` during the pipeline.

---

## 8. How do you authenticate Git using SSH?

**Answer:**

Store the SSH private key as a Jenkins Credential and reference it using the `credentialsId` while cloning the Git repository.

---

## 9. How do you deploy to Kubernetes securely from Jenkins?

**Answer:**

Store the `kubeconfig` file as a Secret File credential and use `withCredentials()` to inject it during deployment.

---

## 10. What are some Jenkins Credentials best practices?

**Answer:**

* Never hardcode secrets.
* Use `withCredentials()` whenever possible.
* Rotate credentials regularly.
* Follow the principle of least privilege.
* Prefer SSH keys or API tokens over passwords.

---

# ⭐ Most Asked Interview Question

**Q:** Why should secrets never be stored inside a Jenkinsfile?

**Answer:**

Because Jenkinsfiles are usually stored in Git repositories. Hardcoding passwords or API keys can expose sensitive information to anyone with repository access. Jenkins Credentials securely encrypt and manage secrets, making CI/CD pipelines safer and production-ready.
