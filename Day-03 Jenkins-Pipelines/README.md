# Day 03 - Jenkins Pipelines

Part of the **100 Days of DevOps** series.

## What This Day Covers

Jenkins Pipelines are the backbone of CI/CD automation in Jenkins. Today's focus is on understanding the two pipeline syntaxes (Declarative and Scripted), how pipeline stages/steps work, and how to structure a production-grade Jenkinsfile.

## Topics

| # | Topic | Folder/File |
|---|-------|-------------|
| 1 | Pipeline Basics & Declarative Syntax | `Part-1/Notes.md` |
| 2 | Scripted Pipelines, Shared Libraries & Advanced Constructs | `Part-2/Notes.md` |
| 3 | Real Jenkinsfile Examples | `Jenkinsfile-Examples/` |
| 4 | Quick Reference | `Cheat-Sheet.md` |
| 5 | Interview Prep | `Interview-Questions.md` |
| 6 | Further Reading | `Resources.md` |

## Folder Structure

```
Day-03-Jenkins-Pipelines/
│
├── README.md
├── Part-1/
│   ├── Jenkins-Day3-Part1.png
│   └── Notes.md
├── Part-2/
│   ├── Jenkins-Day3-Part2.png
│   └── Notes.md
├── Jenkinsfile-Examples/
│   ├── declarative.Jenkinsfile
│   ├── scripted.Jenkinsfile
│   └── production.Jenkinsfile
├── Cheat-Sheet.md
├── Interview-Questions.md
└── Resources.md
```

## Key Takeaways

- **Declarative pipelines** are the recommended approach for most teams — structured, readable, and easier to lint.
- **Scripted pipelines** offer full Groovy flexibility for complex, dynamic logic.
- A production-grade Jenkinsfile combines stages, post-actions, parallelism, credentials handling, and proper error handling.
- Shared Libraries let you DRY up pipeline logic across multiple repos/teams.

## Series Navigation

`< Day 02: Docker Container Lifecycle` | `Day 03: Jenkins Pipelines` | `Day 04: Jenkins Agents & Distributed Builds >`

---
*Part of the 100 Days of DevOps series — follow along on GitHub and LinkedIn.*
