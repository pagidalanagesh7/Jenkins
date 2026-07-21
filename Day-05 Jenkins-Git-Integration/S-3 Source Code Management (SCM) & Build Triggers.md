---

# 3. Source Code Management (SCM) & Build Triggers

Once Jenkins is connected to GitHub, the next step is configuring **Source Code Management (SCM)** and deciding **when Jenkins should start a build**.

SCM tells Jenkins **where the source code is located**, while Build Triggers tell Jenkins **when to start the pipeline**.

Together, these two features automate the entire build process.

---

# What is Source Code Management (SCM)?

Source Code Management (SCM) is a feature in Jenkins that allows it to connect to a Git repository and fetch the latest source code.

Whenever a build starts, Jenkins checks the SCM configuration, downloads the latest code, and passes it to the pipeline.

Without SCM, Jenkins doesn't know:

- Which repository to use
- Which branch to checkout
- Which credentials to use

---

## Configure SCM

Open your Jenkins Pipeline Job.

Navigate to:

```text
Configure

↓

Pipeline

↓

Pipeline Definition

↓

Pipeline script from SCM
```

Choose:

```text
SCM

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

---

## SCM Workflow

```text
Jenkins Job

↓

Read SCM Configuration

↓

Connect to GitHub

↓

Authenticate

↓

Clone Repository

↓

Checkout Branch

↓

Start Pipeline
```

---

## Real-Time Example

Repository

```text
https://github.com/devops-team/jenkins-demo-app.git
```

Branch

```text
main
```

When a build starts, Jenkins automatically executes:

```bash
git clone https://github.com/devops-team/jenkins-demo-app.git

git checkout main
```

The latest source code is then passed to the pipeline for building and testing.

---

# What are Build Triggers?

A Build Trigger tells Jenkins **when to start a build**.

Instead of clicking the **Build Now** button every time, Jenkins can automatically start the pipeline whenever specific events occur.

This is one of the core features of Continuous Integration (CI).

---

## Types of Build Triggers

### 1. Manual Build

A user manually clicks:

```text
Build Now
```

Jenkins immediately starts the pipeline.

Best for:

- Learning
- Testing
- Debugging

---

### 2. Build Periodically

Jenkins starts builds based on a fixed schedule.

Example:

```text
Every day at 10:00 PM
```

Cron Example

```text
0 22 * * *
```

Useful for:

- Nightly Builds
- Scheduled Reports
- Backup Jobs

---

### 3. Poll SCM

Jenkins checks the Git repository at regular intervals.

Example

```text
H/5 * * * *
```

Meaning:

Jenkins checks Git every **5 minutes**.

If it finds new commits, the build starts automatically.

Workflow

```text
Every 5 Minutes

↓

Check Git Repository

↓

New Commit?

↓

Yes

↓

Start Build
```

---

### 4. GitHub Webhook Trigger

Instead of checking repeatedly, GitHub immediately informs Jenkins whenever code is pushed.

Workflow

```text
Developer

↓

git push

↓

GitHub

↓

Webhook

↓

Jenkins

↓

Build Starts Immediately
```

This is the fastest and most efficient trigger.

---

# Poll SCM vs GitHub Webhook

| Poll SCM | GitHub Webhook |
|-----------|----------------|
| Jenkins checks Git repeatedly | GitHub sends notification instantly |
| Slight delay possible | Instant build |
| More server load | Less server load |
| Suitable for small projects | Recommended for production |

---

## Real Production Example

A developer pushes code.

```bash
git add .

git commit -m "Added payment module"

git push origin main
```

If **Poll SCM** is configured:

Jenkins waits until the next polling interval before starting the build.

If **GitHub Webhook** is configured:

Jenkins starts the build immediately after the push.

---

## Production Best Practices

- Use **GitHub Webhooks** instead of Poll SCM whenever possible.
- Keep SCM pointing to the correct production branch.
- Store Jenkinsfile inside the Git repository.
- Verify credentials before enabling automatic builds.
- Use Manual Build only for testing and troubleshooting.

---

## Key Takeaway

SCM tells Jenkins **where to get the source code**, while Build Triggers tell Jenkins **when to start the build**.

In production environments, the most common combination is:

```text
GitHub Repository

↓

SCM

↓

GitHub Webhook

↓

Jenkins Pipeline

↓

Build

↓

Test

↓

Deploy
```

This provides a fast, reliable, and fully automated CI/CD workflow.