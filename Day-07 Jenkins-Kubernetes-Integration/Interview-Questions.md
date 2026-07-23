# 📄 Jenkins + Kubernetes Interview Questions

# 🚀 Jenkins + Kubernetes Integration

## 1. Why do we integrate Jenkins with Kubernetes?

**Answer:**  
To dynamically create build agents, improve scalability, reduce infrastructure costs, and automate CI/CD pipelines.

---

## 2. What is a Kubernetes Agent in Jenkins?

**Answer:**  
A temporary Pod created by Kubernetes to execute a Jenkins pipeline.

---

## 3. What is the Kubernetes Plugin?

**Answer:**  
A Jenkins plugin that allows Jenkins to communicate with Kubernetes and launch dynamic agent Pods.

---

## 4. What is a Pod Template?

**Answer:**  
A Pod Template defines the configuration of the Kubernetes Pod that Jenkins creates for pipeline execution.

---

## 5. Why are Dynamic Agents preferred over Static Agents?

**Answer:**  
Dynamic Agents are created only when required and deleted after the build, saving resources and improving scalability.

---

## 6. What is a Jenkinsfile?

**Answer:**  
A Jenkinsfile is a text file that defines the CI/CD pipeline as code.

---

## 7. What is the role of a Service Account?

**Answer:**  
It provides Jenkins with an identity to securely access the Kubernetes API.

---

## 8. Why is RBAC important?

**Answer:**  
RBAC controls what actions Jenkins can perform inside the Kubernetes cluster, improving security.

---

## 9. What happens after a pipeline finishes?

**Answer:**  
The Kubernetes Agent Pod is automatically deleted to free up resources.

---

## 10. What are the benefits of Jenkins + Kubernetes?

**Answer:**

- Auto Scaling
- Dynamic Agents
- Better Resource Utilization
- Improved Security
- Lower Infrastructure Cost
- Cloud-Native CI/CD

---

# ⭐ Quick Interview Tips

- Understand the Jenkins + Kubernetes architecture.
- Know the Agent lifecycle.
- Learn the purpose of the Kubernetes Plugin.
- Understand Pod Templates and Jenkinsfiles.
- Practice troubleshooting common pipeline issues.