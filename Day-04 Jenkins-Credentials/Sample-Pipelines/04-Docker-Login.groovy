/*
---------------------------------------------------------
Declarative Jenkins Pipeline
Jenkins Learning Series - Day 4
Topic: Docker Login using Jenkins Credentials
---------------------------------------------------------
*/

pipeline {

    agent any

    stages {

        stage('Docker Login') {

            steps {

                withCredentials([
                    usernamePassword(
                        credentialsId: 'dockerhub-creds',
                        usernameVariable: 'USERNAME',
                        passwordVariable: 'PASSWORD'
                    )
                ]) {

                    sh '''
                    echo $PASSWORD | docker login -u $USERNAME --password-stdin
                    '''

                }

            }

        }

    }

}