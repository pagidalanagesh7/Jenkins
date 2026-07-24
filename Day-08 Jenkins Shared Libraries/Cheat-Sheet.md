# Jenkins Shared Libraries – Cheat Sheet

## 📌 What is a Jenkins Shared Library?

A Jenkins Shared Library is a collection of reusable Groovy scripts, pipeline functions, and utility classes that can be shared across multiple Jenkins pipelines.

---

# 📌 Why Use Shared Libraries?

✅ Reuse pipeline code

✅ Reduce duplicate Jenkinsfiles

✅ Centralize CI/CD logic

✅ Improve maintainability

✅ Standardize pipelines

✅ Enterprise-ready automation

---

# 📌 Standard Folder Structure

```text
shared-library/
│
├── vars/
├── src/
├── resources/
└── README.md
```

---

# 📌 Directory Purpose

| Directory | Purpose |
|-----------|---------|
| **vars/** | Global pipeline functions |
| **src/** | Groovy classes and utilities |
| **resources/** | YAML, JSON, HTML, templates |

---

# 📌 Load a Shared Library

```groovy
@Library('company-library') _
```

---

# 📌 Sample Pipeline Function

```groovy
buildApp()
runTests()
dockerBuild()
deployApp()
notifySlack()
```

---

# 📌 Example `call()` Method

```groovy
def call() {
    echo "Building Application..."
}
```

---

# 📌 Workflow

```text
Developer
    │
Git Push
    │
GitHub
    │
Jenkins Pipeline
    │
Load Shared Library
    │
Build
    │
Test
    │
Docker Build
    │
Deploy
    │
Notification
```

---

# 📌 Global vs Folder Library

| Global Library | Folder Library |
|---------------|----------------|
| Available to all pipelines | Available only inside a folder |
| Managed by Jenkins Admin | Managed by Team/Folder Admin |
| Common enterprise pipelines | Team-specific pipelines |

---

# 📌 Advantages

- Code Reusability
- Cleaner Jenkinsfiles
- Centralized Logic
- Easy Maintenance
- Better Collaboration
- Standardized CI/CD
- Faster Development
- Enterprise Scalability

---

# 📌 Best Practices

- Keep functions small and reusable.
- Use meaningful function names.
- Store secrets in Jenkins Credentials.
- Version Shared Libraries.
- Follow modular design.
- Document every reusable function.
- Test changes before production.
- Keep business logic inside `src`.
- Store templates in `resources`.
- Review library changes before merging.

---

# 📌 Common Mistakes

- Copy-pasting Jenkinsfiles
- Hardcoding credentials
- Large reusable functions
- Poor folder structure
- No versioning
- Missing documentation
- Mixing business logic with pipeline code

---

# 📌 Interview Keywords

- Shared Library
- Groovy
- `@Library`
- `vars`
- `src`
- `resources`
- `call()`
- Global Library
- Folder Library
- Pipeline Reusability
- CI/CD Standardization

---

# 📌 Quick Revision

✔ Reusable Pipeline Code

✔ Written in Groovy

✔ Stored in Git Repository

✔ Loaded using `@Library`

✔ Uses `vars`, `src`, and `resources`

✔ Supports Global & Folder Libraries

✔ Reduces Duplicate Jenkinsfiles

✔ Enterprise CI/CD Best Practice

---

# 🚀 One-Line Summary

> **Write pipeline logic once, reuse it everywhere. Jenkins Shared Libraries make CI/CD pipelines cleaner, scalable, maintainable, and production-ready.**