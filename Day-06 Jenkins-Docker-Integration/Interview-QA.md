# 🎯 Jenkins Docker Integration - Interview Questions & Answers

## 1. What is Jenkins Docker Integration?

Jenkins Docker Integration allows Jenkins to build, run, test, and manage Docker containers as part of a CI/CD pipeline.

---

## 2. Why do we use Docker with Jenkins?

Docker provides a consistent build environment, eliminates dependency issues, and makes applications portable across different environments.

---

## 3. What is a Docker Image?

A Docker Image is a read-only template that contains an application along with its dependencies.

---

## 4. What is a Docker Container?

A Docker Container is a running instance of a Docker Image.

---

## 5. What is the purpose of a Dockerfile?

A Dockerfile contains instructions used to build a Docker Image.

---

## 6. Which Jenkins Pipeline method is used to build a Docker image?

```groovy
docker.build("my-app:v1")
```

---

## 7. What is the equivalent Docker command for `docker.build()`?

```bash
docker build -t my-app:v1 .
```

---

## 8. How does Jenkins communicate with Docker?

Jenkins communicates with the Docker Engine using the Docker Pipeline Plugin and the Docker API.

---

## 9. What are the advantages of Jenkins Docker Integration?

- Consistent build environment
- Faster deployments
- Easy application packaging
- Better scalability
- Improved CI/CD automation

---

## 10. Name some Docker Pipeline methods.

- docker.build()
- docker.image()
- docker.withRegistry()
- docker.inside()

---

## 11. Where are Docker images stored?

Docker images are stored in a Docker Registry such as:

- Docker Hub
- Amazon ECR
- Azure Container Registry (ACR)
- Google Artifact Registry (GAR)

---

## 12. What are some production best practices?

- Use versioned image tags.
- Store credentials in Jenkins Credentials.
- Use lightweight base images.
- Scan Docker images for vulnerabilities.
- Avoid using the `latest` tag in production.

---

# ⭐ Quick Revision

✅ Jenkins automates CI/CD pipelines.

✅ Docker packages applications into containers.

✅ `docker.build()` builds Docker images.

✅ Dockerfile defines how an image is built.

✅ Docker Registry stores Docker images.

✅ Jenkins + Docker enables reliable and repeatable deployments.