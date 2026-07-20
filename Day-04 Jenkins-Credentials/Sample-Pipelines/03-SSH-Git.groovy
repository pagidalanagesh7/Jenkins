/*
---------------------------------------------------------
Declarative Jenkins Pipeline
Jenkins Learning Series - Day 4
Topic: Git Authentication using SSH Key
---------------------------------------------------------
*/

pipeline {

    agent any

    stages {

        stage('Clone Repository') {

            steps {

                git(
                    url: 'git@github.com:your-username/sample-repo.git',
                    branch: 'main',
                    credentialsId: 'github-ssh'
                )

            }

        }

    }

}