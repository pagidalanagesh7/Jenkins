/*
-------------------------------------------------------------------
Production Jenkins Pipeline
Spring Boot + Maven + Docker + Kubernetes
Jenkins Learning Series - Day 3
-------------------------------------------------------------------
*/

pipeline {

    agent any

    tools {
        maven 'Maven-3.9'
        jdk 'JDK-17'
    }

    environment {

        APP_NAME = "springboot-app"

        DOCKER_IMAGE = "company/springboot-app"

        VERSION = "${BUILD_NUMBER}"

        DOCKER_CREDENTIALS = credentials('dockerhub-creds')

    }

    options {

        timestamps()

        disableConcurrentBuilds()

        buildDiscarder(logRotator(
            numToKeepStr: '10'
        ))

    }

    stages {

        stage('Checkout') {

            steps {

                git branch: 'main',
                url: 'https://github.com/company/springboot-app.git'

            }

        }

        stage('Build') {

            steps {

                sh 'mvn clean package'

            }

        }

        stage('Unit Test') {

            steps {

                sh 'mvn test'

            }

        }

        stage('Docker Build') {

            steps {

                sh """
                docker build \
                -t ${DOCKER_IMAGE}:${VERSION} .
                """

            }

        }

        stage('Docker Push') {

            steps {

                sh """
                docker login \
                -u ${DOCKER_CREDENTIALS_USR} \
                -p ${DOCKER_CREDENTIALS_PSW}

                docker push ${DOCKER_IMAGE}:${VERSION}
                """

            }

        }

        stage('Deploy to Kubernetes') {

            steps {

                sh '''
                kubectl apply -f k8s/deployment.yaml
                kubectl apply -f k8s/service.yaml
                '''

            }

        }

    }

    post {

        success {

            echo 'Deployment completed successfully.'

        }

        failure {

            echo 'Deployment failed.'

        }

        always {

            cleanWs()

        }

    }

}