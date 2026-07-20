/*
---------------------------------------------------------
Declarative Jenkins Pipeline
Jenkins Learning Series - Day 4
Topic: Secret Text Credentials
---------------------------------------------------------
*/

pipeline {

    agent any

    environment {
        GITHUB_TOKEN = credentials('github-token')
    }

    stages {

        stage('Verify Secret') {
            steps {
                echo 'Secret Text Credential Loaded Successfully.'

                sh '''
                echo "GitHub Token is securely available."
                echo "Token Length: ${#GITHUB_TOKEN}"
                '''
            }
        }

    }

    post {

        always {
            echo 'Pipeline Completed.'
        }

    }

}