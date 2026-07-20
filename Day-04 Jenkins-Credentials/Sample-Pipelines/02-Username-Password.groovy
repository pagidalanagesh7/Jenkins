/*
---------------------------------------------------------
Declarative Jenkins Pipeline
Jenkins Learning Series - Day 4
Topic: Username & Password Credentials
---------------------------------------------------------
*/

pipeline {

    agent any

    stages {

        stage('Login') {

            steps {

                withCredentials([
                    usernamePassword(
                        credentialsId: 'dockerhub-creds',
                        usernameVariable: 'USERNAME',
                        passwordVariable: 'PASSWORD'
                    )
                ]) {

                    sh '''
                    echo "Logging in to Docker Hub..."
                    echo $PASSWORD | docker login -u $USERNAME --password-stdin
                    '''

                }

            }

        }

    }

}