# Jenkins Day-05 Interview Questions & Troubleshooting

This document contains commonly asked Jenkins Git Integration interview questions along with real-time production troubleshooting scenarios.

---

# Basic Interview Questions

## 1. What is Git Integration in Jenkins?

Git Integration allows Jenkins to connect with a Git repository such as GitHub, GitLab, or Bitbucket to automatically fetch the latest source code and execute CI/CD pipelines.

---

## 2. Why do we integrate Jenkins with GitHub?

GitHub stores the application source code, while Jenkins automates the build, test, and deployment process whenever code changes are pushed.

---

## 3. Which Git providers are supported by Jenkins?

- GitHub
- GitLab
- Bitbucket
- Azure Repos
- AWS CodeCommit
- Any standard Git repository

---

## 4. Which Jenkins plugin is required for Git integration?

The **Git Plugin** is required to connect Jenkins with Git repositories.

---

## 5. What authentication methods are supported?

- Personal Access Token (PAT)
- SSH Key
- Username & Password (Legacy)

Production environments generally use **SSH Keys** or **PAT**.

---

## 6. What is a Personal Access Token (PAT)?

A Personal Access Token is a secure authentication token generated in GitHub that replaces passwords for Git operations over HTTPS.

---

## 7. Why are GitHub passwords no longer used?

GitHub has discontinued password authentication for Git operations due to security reasons. PATs and SSH Keys provide better security.

---

## 8. What is Source Code Management (SCM)?

SCM is the Jenkins configuration that tells Jenkins where the source code is located and which branch to build.

---

## 9. What information is required in SCM?

- Repository URL
- Branch Name
- Credentials

---

## 10. What is the difference between Poll SCM and GitHub Webhook?

| Poll SCM | GitHub Webhook |
|----------|----------------|
| Jenkins checks Git periodically | GitHub notifies Jenkins instantly |
| More server usage | Lower server usage |
| Slight delay | Immediate build |
| Suitable for testing | Recommended for production |

---

## 11. What is a GitHub Webhook?

A Webhook is an HTTP callback that automatically notifies Jenkins whenever a selected GitHub event (such as a code push) occurs.

---

## 12. What is a Jenkinsfile?

A Jenkinsfile is a text file written in Groovy that defines the complete CI/CD pipeline as code.

---

## 13. Where should a Jenkinsfile be stored?

The Jenkinsfile should be stored in the root directory of the Git repository.

---

## 14. What is Pipeline as Code?

Pipeline as Code means storing the pipeline definition inside a Jenkinsfile in the Git repository instead of configuring it manually in the Jenkins UI.

---

## 15. Why are GitHub Webhooks preferred over Poll SCM?

Because they trigger builds immediately after a code push and reduce unnecessary polling, making CI/CD faster and more efficient.

---

# Production Troubleshooting Scenarios

## 16. Jenkins cannot clone the GitHub repository.

### Possible Causes

- Invalid Repository URL
- Wrong Credentials
- Network issue
- Repository deleted

### Resolution

- Verify the repository URL.
- Check PAT or SSH credentials.
- Test GitHub connectivity.
- Confirm repository permissions.

---

## 17. Authentication Failed

### Possible Causes

- Expired PAT
- Invalid SSH Key
- Incorrect credentials

### Resolution

- Generate a new PAT.
- Update Jenkins Credentials.
- Verify repository access.

---

## 18. GitHub Webhook is not triggering Jenkins.

### Possible Causes

- Incorrect Payload URL
- Webhook disabled
- Firewall blocking requests

### Resolution

- Verify the Payload URL.
- Check GitHub Webhook delivery logs.
- Ensure Jenkins is reachable from GitHub.

---

## 19. Git command not found

### Possible Causes

Git is not installed or not configured in Jenkins.

### Resolution

- Install Git.
- Configure Git under **Manage Jenkins → Tools**.
- Verify using:

```bash
git --version
```

---

## 20. Repository Not Found

### Possible Causes

- Incorrect Repository URL
- No repository permission
- Repository renamed

### Resolution

- Verify repository URL.
- Check GitHub permissions.
- Update the Jenkins job configuration.

---

## 21. Jenkinsfile Not Found

### Possible Causes

- Jenkinsfile missing
- Wrong SCM branch
- Incorrect file name

### Resolution

- Ensure the file is named **Jenkinsfile**.
- Store it in the repository root.
- Verify the configured branch.

---

## 22. Permission Denied (publickey)

### Possible Causes

- SSH Key not configured
- Public key missing in GitHub

### Resolution

- Generate an SSH Key pair.
- Add the public key to GitHub.
- Store the private key in Jenkins Credentials.

---

## 23. Build Trigger Not Working

### Possible Causes

- Trigger not enabled
- Webhook missing
- Poll SCM disabled

### Resolution

- Enable the required build trigger.
- Configure GitHub Webhook correctly.
- Verify Jenkins job settings.

---

## 24. Wrong Branch is Building

### Possible Causes

- Incorrect branch configured in SCM

### Resolution

Verify the branch configuration.

Example:

```text
*/main
```

or

```text
*/develop
```

---

## 25. Build Fails After Checkout

### Possible Causes

- Build script error
- Missing dependencies
- Environment issue

### Resolution

- Review console logs.
- Install required dependencies.
- Verify build commands locally.

---

# Interview Tips

- Explain concepts with real-world examples.
- Know the difference between PAT and SSH.
- Understand Poll SCM and GitHub Webhooks.
- Learn how Jenkins Credentials work.
- Be comfortable reading Jenkins console logs.
- Practice troubleshooting common Git integration issues.
- Always follow production security best practices.

---

# Key Takeaway

Jenkins Git Integration is one of the most frequently asked DevOps interview topics. A good understanding of Git Plugin configuration, SCM, GitHub Webhooks, Jenkinsfile, authentication methods, and troubleshooting scenarios will help you confidently answer both beginner and production-level interview questions.