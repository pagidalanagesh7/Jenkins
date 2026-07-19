# Resources — Jenkins Pipelines

## Official Documentation
- [Jenkins Pipeline Syntax Reference](https://www.jenkins.io/doc/book/pipeline/syntax/)
- [Jenkins Pipeline Steps Reference](https://www.jenkins.io/doc/pipeline/steps/)
- [Shared Libraries Documentation](https://www.jenkins.io/doc/book/pipeline/shared-libraries/)
- [Kubernetes Plugin for Jenkins Agents](https://plugins.jenkins.io/kubernetes/)
- [AWS Credentials Plugin](https://plugins.jenkins.io/aws-credentials/)

## Plugins Worth Knowing
- **Blue Ocean** — modern visual pipeline UI
- **Kubernetes Plugin** — dynamic pod-based agents
- **EC2 Fleet Plugin** — auto-scaling EC2 build agents
- **Lockable Resources** — prevent overlapping deployments
- **Pipeline: AWS Steps** — native AWS step wrappers

## Further Reading
- [TMS Outsource — Git & DevOps Blog](https://tms-outsource.com/blog/)

## Related Days in This Series
- `Day 01` — Linux Fundamentals for DevOps
- `Day 02` — Docker Container Lifecycle
- `Day 03` — Jenkins Pipelines *(this day)*
- `Day 04` — Jenkins Agents & Distributed Builds *(next)*

## Practice
- Try converting the `scripted.Jenkinsfile` example in this repo into a Declarative pipeline using `matrix { }` for the multi-service builds.
- Set up a local Jenkins instance (Docker or Rancher Desktop) and wire a Shared Library repo to test the `vars/buildAndDeploy.groovy` pattern from Part-2 notes.
