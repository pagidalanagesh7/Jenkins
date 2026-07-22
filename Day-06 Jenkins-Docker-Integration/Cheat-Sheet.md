# 📝 Jenkins Docker Integration Cheat Sheet

## Build Docker Image

```groovy
docker.build("my-app:v1")
```

Equivalent Command:

```bash
docker build -t my-app:v1 .
```

---

## Run Docker Container

```bash
docker run -d -p 8080:8080 my-app:v1
```

---

## List Docker Images

```bash
docker images
```

---

## List Running Containers

```bash
docker ps
```

---

## Stop Container

```bash
docker stop <container-id>
```

---

## Remove Container

```bash
docker rm <container-id>
```

---

## Remove Docker Image

```bash
docker rmi my-app:v1
```

---

# Dockerfile Instructions

| Instruction | Purpose |
|-------------|---------|
| FROM | Base Image |
| WORKDIR | Working Directory |
| COPY | Copy Files |
| EXPOSE | Expose Port |
| ENTRYPOINT | Start Application |

---

# Jenkins Pipeline Keywords

- pipeline
- agent
- stages
- stage
- steps
- script
- post

---

# Docker Pipeline Methods

- docker.build()
- docker.image()
- docker.withRegistry()
- docker.inside()

---

# Best Practices

- Use versioned image tags.
- Keep Dockerfiles simple.
- Store secrets in Jenkins Credentials.
- Avoid using the `latest` tag in production.
- Use lightweight base images.

---

# Quick Workflow

```text
GitHub
   │
   ▼
Jenkins
   │
   ▼
Build Application
   │
   ▼
Build Docker Image
   │
   ▼
Push to Registry
   │
   ▼
Deploy
```