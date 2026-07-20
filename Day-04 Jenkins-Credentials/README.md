# 🚀 Jenkins Learning Series – Day 4

# 🔐 Jenkins Credentials & Secrets Management

Secure Your CI/CD Pipelines Like an Enterprise DevOps Engineer

---

## 📖 Overview

In this module, you'll learn how Jenkins securely manages sensitive information such as passwords, API tokens, SSH keys, and secret files. Instead of hardcoding secrets inside pipelines, Jenkins provides a secure Credentials Management system that encrypts and injects secrets only when needed.

This guide covers everything from credential types to real-world production examples using Git, Docker, and Kubernetes.

---

## 📚 Topics Covered

* What are Jenkins Credentials?
* Why Credentials are Required
* Types of Jenkins Credentials
* Jenkins Credentials Store
* Adding Credentials in Jenkins
* Using `credentials()` in Jenkinsfile
* Using `withCredentials()`
* Docker Login using Credentials
* Git Authentication using SSH Keys
* Kubernetes Deployment using kubeconfig
* Environment Variables vs Credentials
* Jenkins Credentials Best Practices
* Common Mistakes to Avoid
* Production Tips
* Interview Questions

---

## 📂 Repository Structure

```text
Day-04-Jenkins-Credentials/
│
├── README.md
├── Theory.md
├── Cheat-Sheet.md
├── Interview-Questions.md
├── Resources.md
└── Jenkins-Credentials-Infographic.png
```

---

## 🎯 Learning Objectives

After completing this module, you will be able to:

* Understand how Jenkins securely stores secrets.
* Configure different types of Jenkins Credentials.
* Use credentials safely inside Jenkins Pipelines.
* Authenticate with Git repositories using SSH keys.
* Log in to Docker Hub securely from Jenkins.
* Deploy applications to Kubernetes using kubeconfig credentials.
* Follow enterprise security best practices for CI/CD pipelines.

---

## 💼 Real-World Use Cases

* Secure GitHub repository access
* Docker Hub authentication
* Kubernetes cluster deployments
* Cloud provider authentication
* API token management
* SSH-based server deployments
* Enterprise CI/CD pipelines

---

## 📌 Prerequisites

* Basic knowledge of Jenkins
* Jenkins installed and running
* Familiarity with Jenkins Pipelines
* Basic understanding of Git, Docker, and Kubernetes

---

## 📖 Related Files

* **Theory.md** – Complete explanation of Jenkins Credentials and Secrets Management
* **Cheat-Sheet.md** – Quick revision notes
* **Interview-Questions.md** – Frequently asked interview questions with answers
* **Resources.md** – Official documentation and learning resources
* **Jenkins-Credentials-Infographic.png** – Handwritten visual notes

---

## ⭐ Key Takeaway

Never hardcode secrets in your Jenkins Pipelines. Always use Jenkins Credentials to securely manage passwords, API tokens, SSH keys, and secret files. Proper credential management is essential for building secure, scalable, and production-ready CI/CD pipelines.

---

Happy Learning! 🚀
