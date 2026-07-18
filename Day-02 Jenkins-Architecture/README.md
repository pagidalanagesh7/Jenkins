# 🚀 Jenkins Learning Series – Day 2

# 🏗️ Jenkins Architecture Explained

Learn how Jenkins works behind the scenes using its **Controller-Agent Architecture**. This guide covers the core components, build execution flow, and enterprise best practices used in modern CI/CD environments.

---

## 📚 What You'll Learn

- ✅ What is Jenkins Architecture?
- ✅ Controller vs Agent
- ✅ Executors and Workspaces
- ✅ Build Queue
- ✅ Complete Jenkins Build Flow
- ✅ Distributed Jenkins Architecture
- ✅ Enterprise CI/CD Workflow
- ✅ Production Best Practices
- ✅ Interview Questions

---

## 📂 Repository Structure

```text
Day-02-Jenkins-Architecture/

├── README.md
├── Theory.md
├── Cheat-Sheet.md
├── Interview-Questions.md
├── Resources.md
└── Jenkins-Day2-Architecture.png
```

---

## 🏗️ Jenkins Architecture

```text
                Developer
                     │
              Push Source Code
                     │
                     ▼
             Git Repository
                     │
              Webhook Trigger
                     │
                     ▼
          Jenkins Controller
                     │
      Schedules & Assigns Jobs
                     │
     ┌───────────────┼───────────────┐
     ▼               ▼               ▼
 Linux Agent    Windows Agent   Docker Agent
     │               │               │
     └───────────────┼───────────────┘
                     │
                     ▼
              Build • Test • Deploy
```

---

## 🔄 Jenkins Build Flow

```text
Developer
    │
    ▼
GitHub
    │
Webhook
    ▼
Jenkins Controller
    │
Available Agent
    ▼
Checkout Code
    ▼
Build
    ▼
Test
    ▼
Package
    ▼
Docker Build
    ▼
Deploy
    ▼
Notification
```

---

## 📁 Files in This Repository

| File | Description |
|------|-------------|
| **Theory.md** | Complete explanation of Jenkins Architecture |
| **Cheat-Sheet.md** | Quick revision notes |
| **Interview-Questions.md** | Frequently asked Jenkins Architecture interview questions |
| **Resources.md** | Official documentation, books, and learning resources |
| **Jenkins-Day2-Architecture.png** | Handwritten infographic |

---

## 🎯 Key Takeaways

- Jenkins follows a **Controller-Agent Architecture**.
- The **Controller** manages Jenkins and schedules jobs.
- **Agents** execute build, test, and deployment tasks.
- **Executors** enable parallel build execution.
- The **Build Queue** manages waiting jobs.
- Distributed Jenkins improves scalability, reliability, and performance.

---

## 🎓 Prerequisites

Before starting this topic, you should be familiar with:

- Jenkins Fundamentals
- Continuous Integration (CI)
- Continuous Delivery (CD)
- Basic Git & GitHub concepts

---

## 📚 Recommended Learning Order

1. 📖 Read **Theory.md**
2. 📝 Revise using **Cheat-Sheet.md**
3. 🎯 Practice with **Interview-Questions.md**
4. 🔗 Explore **Resources.md**
5. 🖼️ Review the handwritten infographic

---

## 🚀 Next Topic

➡️ **Day 3 – Jenkins Pipelines & Jenkinsfile**

We'll cover:

- Declarative vs Scripted Pipelines
- Jenkinsfile Structure
- Stages & Steps
- Environment Variables
- Parameters
- Post Actions
- Pipeline Best Practices

---

## ⭐ Support

If you found this repository helpful:

- ⭐ Star the repository
- 🍴 Fork it for your own learning
- 📢 Share it with the DevOps community
- 🤝 Connect with me on LinkedIn

Happy Learning! 🚀