# 📄 Jenkins + Kubernetes Cheat Sheet

# 🚀 Jenkins + Kubernetes Integration

## Jenkins Components

- Jenkins Controller
- Kubernetes Plugin
- Dynamic Agent Pod
- Jenkinsfile
- Pod Template

---

## CI/CD Workflow

Developer

↓

Git Push

↓

GitHub

↓

Webhook

↓

Jenkins Controller

↓

Kubernetes Agent Pod

↓

Build

↓

Test

↓

Deploy

↓

Pod Deleted

---

## Key Concepts

- Dynamic Agents are created only when needed.
- Every pipeline gets a fresh Kubernetes Pod.
- Pod Templates define the Agent configuration.
- Jenkinsfile defines the pipeline workflow.
- Kubernetes Plugin connects Jenkins with Kubernetes.

---

## Benefits

- Auto Scaling
- Better Resource Utilization
- Lower Infrastructure Cost
- Secure & Isolated Builds
- Cloud-Native CI/CD

---

## Best Practices

- Use Dynamic Agents
- Follow Least Privilege RBAC
- Store Secrets Securely
- Keep Agent Images Lightweight
- Configure Resource Limits
- Monitor Pipeline Execution

---

## Common Issues

| Issue | Solution |
|--------|----------|
| Agent Offline | Verify Kubernetes Connection |
| Pending Pod | Check Cluster Resources |
| ImagePullBackOff | Verify Container Image |
| Permission Denied | Check RBAC |
| Build Failed | Review Pipeline Logs |

---

## Quick Revision

✅ Jenkins Controller manages pipelines.

✅ Kubernetes creates temporary Agent Pods.

✅ Pod Templates define Agent configuration.

✅ Jenkinsfile controls the CI/CD pipeline.

✅ Dynamic Agents reduce infrastructure costs.

✅ RBAC secures Kubernetes access.

---

🚀 **Interview Tip:**  
**Remember the workflow:**  
**Git Push → Webhook → Jenkins → Kubernetes Agent → Build → Deploy → Agent Deleted**