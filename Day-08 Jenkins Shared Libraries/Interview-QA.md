# Jenkins Shared Libraries – Interview Questions & Answers

## 1. What is a Jenkins Shared Library?

A Jenkins Shared Library is a collection of reusable Groovy scripts, pipeline functions, and utility classes that can be shared across multiple Jenkins pipelines. It helps reduce duplicate code and standardize CI/CD workflows across projects.

---

## 2. Why do we use Shared Libraries?

Shared Libraries help to:

- Reuse pipeline code
- Reduce duplicate Jenkinsfiles
- Improve maintainability
- Standardize CI/CD processes
- Simplify enterprise pipeline management

---

## 3. What are the main directories in a Jenkins Shared Library?

A standard Shared Library contains:

- **vars/** – Global pipeline functions
- **src/** – Groovy classes and utility code
- **resources/** – Static files like YAML, JSON, HTML, and templates

---

## 4. What is the purpose of the `vars` directory?

The `vars` directory contains reusable pipeline functions. Each Groovy file becomes a global pipeline step that can be called directly from a Jenkinsfile.

Example:

```groovy
buildApp()
```

---

## 5. What is the purpose of the `src` directory?

The `src` directory stores reusable Groovy classes. It is mainly used for complex logic, utility methods, helper classes, and object-oriented programming.

---

## 6. What is the purpose of the `resources` directory?

The `resources` directory stores static files such as:

- YAML files
- JSON files
- HTML templates
- Kubernetes manifests
- Shell scripts

These files can be loaded during pipeline execution.

---

## 7. How do you load a Shared Library?

Using the `@Library` annotation.

Example:

```groovy
@Library('company-library') _
```

---

## 8. What is a Global Shared Library?

A Global Shared Library is configured in Jenkins and is available to all pipelines in the Jenkins instance.

---

## 9. What is a Folder Shared Library?

A Folder Shared Library is available only to pipelines inside a specific Jenkins folder. It provides better isolation for different teams or projects.

---

## 10. What are the advantages of Jenkins Shared Libraries?

- Reusable code
- Cleaner Jenkinsfiles
- Centralized pipeline logic
- Easier maintenance
- Faster development
- Better collaboration
- Enterprise scalability

---

## 11. Where are Shared Libraries stored?

Shared Libraries are usually stored in a Git repository such as GitHub, GitLab, or Bitbucket.

---

## 12. Which language is used to create Shared Libraries?

Shared Libraries are written using **Groovy**.

---

## 13. What is the `call()` method?

The `call()` method allows a Groovy file inside the `vars` directory to behave like a pipeline step.

Example:

```groovy
def call() {
    echo "Building Application..."
}
```

---

## 14. How do enterprises manage Shared Libraries?

Most organizations:

- Store libraries in Git
- Use version control
- Follow code reviews
- Maintain documentation
- Reuse libraries across multiple projects

---

## 15. What are some best practices?

- Keep functions small
- Use meaningful names
- Store secrets in Jenkins Credentials
- Version the library
- Write proper documentation
- Follow modular design
- Test library changes before production

---

## 16. What are common mistakes?

- Copy-pasting pipeline code
- Hardcoding credentials
- Large reusable functions
- Poor documentation
- No version control
- Incorrect folder structure

---

## 17. What is the difference between `vars` and `src`?

| vars | src |
|------|-----|
| Global pipeline steps | Groovy classes |
| Directly callable | Imported into scripts |
| Simple reusable functions | Complex business logic |

---

## 18. Can one Shared Library be used by multiple projects?

Yes. A single Shared Library can be shared across hundreds of Jenkins pipelines, making CI/CD workflows consistent and easier to maintain.

---

## 19. Why are Shared Libraries important in enterprise environments?

Large organizations manage hundreds of repositories. Shared Libraries eliminate duplicate pipeline code, improve consistency, reduce maintenance, and simplify CI/CD automation.

---

## 20. Explain Jenkins Shared Libraries in one sentence.

**Jenkins Shared Libraries centralize reusable pipeline code, making Jenkins pipelines cleaner, scalable, and easier to maintain across multiple projects.**

---

# Quick Revision

✔ Reusable Pipeline Code

✔ Written in Groovy

✔ Stored in Git Repository

✔ Loaded using `@Library`

✔ Uses `vars`, `src`, and `resources`

✔ Supports Global and Folder Libraries

✔ Reduces Duplicate Jenkinsfiles

✔ Enterprise CI/CD Best Practice