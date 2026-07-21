---

# 2. GitHub Integration & Git Credentials

After installing the Git Plugin, the next step is connecting Jenkins with a GitHub repository.

Once the integration is complete, Jenkins can automatically download the latest source code whenever a build starts. This is how most companies automate their CI/CD pipelines.

---

## What is GitHub Integration?

GitHub Integration is the process of connecting a GitHub repository with Jenkins.

After the connection is established, Jenkins can:

- Clone the repository.
- Download the latest source code.
- Checkout a specific branch.
- Build the application.
- Run automated tests.
- Deploy the application.

This eliminates the need to manually copy project files into Jenkins.

---

## Prerequisites

Before connecting Jenkins with GitHub, make sure you have:

- Jenkins installed and running.
- Git installed on the Jenkins server.
- Git Plugin installed.
- A GitHub account.
- A GitHub repository.
- Git credentials (PAT or SSH Key).

---

## Step 1: Create a GitHub Repository

Log in to your GitHub account and create a new repository.

Example Repository Name

```text
jenkins-demo-app
```

Example Repository URL

```text
https://github.com/devops-team/jenkins-demo-app.git
```

This repository usually contains:

- Application Source Code
- Jenkinsfile
- README.md
- Dockerfile
- Kubernetes YAML Files

---

## Step 2: Create a Jenkins Pipeline

Open Jenkins Dashboard.

Click

```text
New Item
```

Enter a Job Name.

Example

```text
Demo-Pipeline
```

Select

```text
Pipeline
```

Click **OK**.

---

## Step 3: Configure Source Code Management (SCM)

Open the Jenkins job.

Navigate to:

```text
Configure

↓

Source Code Management

↓

Git
```

Enter the repository details.

| Setting | Example |
|----------|----------|
| Repository URL | https://github.com/devops-team/jenkins-demo-app.git |
| Branch | */main |
| Credentials | github-pat |

Save the configuration.

Jenkins now knows where to fetch the application source code.

---

## What Happens After Saving?

Whenever Jenkins starts a build, it performs the following steps.

```text
Connect to GitHub

↓

Authenticate

↓

Clone Repository

↓

Checkout main Branch

↓

Download Latest Code

↓

Start Pipeline
```

---

# Git Credentials

Most enterprise repositories are private.

Jenkins must authenticate with GitHub before accessing the repository.

Without valid credentials, Jenkins cannot clone the repository or download the latest source code.

GitHub supports two recommended authentication methods:

- Personal Access Token (PAT)
- SSH Key Authentication

GitHub no longer supports account passwords for Git operations over HTTPS.

---

## Personal Access Token (PAT)

A Personal Access Token (PAT) is a secure token generated from your GitHub account.

Instead of using your GitHub password, Jenkins uses the PAT to authenticate.

PAT is commonly used with HTTPS repositories.

---

## Add PAT in Jenkins

Navigate to:

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

Select

```text
Kind: Username with Password
```

Example Configuration

| Field | Example |
|--------|----------|
| Username | devops-user |
| Password | ghp_xxxxxxxxxxxxxxxxx |
| ID | github-pat |
| Description | GitHub Personal Access Token |

Click **Save**.

---

## SSH Key Authentication

SSH Authentication uses a pair of cryptographic keys.

- Private Key → Stored securely inside Jenkins.
- Public Key → Added to your GitHub account.

Whenever Jenkins connects to GitHub, GitHub verifies the SSH Key and grants access without asking for a password.

SSH is the preferred authentication method in production environments.

---

## HTTPS vs SSH

| HTTPS (PAT) | SSH Key |
|--------------|----------|
| Easy to configure | Slightly complex initial setup |
| Uses Personal Access Token | Uses SSH Key Pair |
| Good for beginners | Preferred in production |
| Suitable for small projects | Best for enterprise environments |

---

## Authentication Flow

```text
Jenkins

↓

Uses PAT / SSH Key

↓

GitHub Authentication

↓

Access Granted

↓

Clone Repository

↓

Checkout Branch

↓

Pipeline Starts
```

---

## Real-Time Example

A developer pushes a new feature to GitHub.

```bash
git add .
git commit -m "Added user profile feature"
git push origin main
```

Jenkins authenticates using the configured credentials.

If authentication is successful:

```text
Connect to GitHub

↓

Clone Repository

↓

Download Latest Code

↓

Start Build

↓

Build Successful
```

---

## Common Authentication Errors

### Authentication Failed

Reason:

Incorrect PAT or SSH Key.

Solution:

Verify the credentials stored in Jenkins.

---

### Permission Denied

Reason:

Public SSH Key is not added to GitHub.

Solution:

Upload the correct Public Key to your GitHub account.

---

### Repository Not Found

Reason:

Incorrect Repository URL or insufficient permissions.

Solution:

Verify the repository URL and repository access.

---

## Production Best Practices

- Prefer SSH Keys for production environments.
- Use PAT instead of GitHub passwords.
- Never hardcode credentials inside Jenkinsfile.
- Store all credentials in Jenkins Credentials Manager.
- Rotate PATs and SSH Keys regularly.
- Grant only the minimum required permissions.
- Remove unused credentials immediately.

---

## Key Takeaway

Connecting Jenkins with GitHub allows Jenkins to automatically download the latest source code before every build.

Secure authentication using PAT or SSH Keys ensures safe access to private repositories and forms the foundation of a production-ready Jenkins CI/CD pipeline.