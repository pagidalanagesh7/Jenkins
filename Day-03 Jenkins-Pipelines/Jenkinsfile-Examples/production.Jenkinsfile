@Library('company-shared-library') _

pipeline {
    agent {
        kubernetes {
            label 'jenkins-agent-eks'
            defaultContainer 'builder'
        }
    }

    environment {
        AWS_REGION      = 'us-east-1'
        ECR_REGISTRY    = "<account-id>.dkr.ecr.us-east-1.amazonaws.com"
        APP_NAME        = 'watsonx-service'
        IMAGE_TAG       = "${env.GIT_COMMIT.take(7)}-${env.BUILD_NUMBER}"
        SLACK_CHANNEL   = '#devops-alerts'
    }

    options {
        timestamps()
        ansiColor('xterm')
        buildDiscarder(logRotator(numToKeepStr: '30'))
        disableConcurrentBuilds()
        timeout(time: 45, unit: 'MINUTES')
    }

    triggers {
        pollSCM('H/5 * * * *')
    }

    parameters {
        choice(name: 'DEPLOY_ENV', choices: ['dev', 'staging', 'prod'], description: 'Target environment')
        booleanParam(name: 'SKIP_TESTS', defaultValue: false, description: 'Skip test stage (emergency only)')
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
                script {
                    env.GIT_COMMIT_SHORT = sh(script: "git rev-parse --short HEAD", returnStdout: true).trim()
                }
            }
        }

        stage('Static Analysis') {
            parallel {
                stage('Lint') {
                    steps { sh 'npm run lint' }
                }
                stage('Security Scan') {
                    steps { sh 'trivy fs --severity HIGH,CRITICAL .' }
                }
                stage('Dependency Check') {
                    steps { sh 'npm audit --audit-level=high' }
                }
            }
        }

        stage('Build') {
            steps {
                sh "docker build -t ${ECR_REGISTRY}/${APP_NAME}:${IMAGE_TAG} ."
            }
        }

        stage('Test') {
            when {
                expression { !params.SKIP_TESTS }
            }
            steps {
                sh "docker run --rm ${ECR_REGISTRY}/${APP_NAME}:${IMAGE_TAG} npm run test:ci"
            }
            post {
                always {
                    junit 'test-results/*.xml'
                }
            }
        }

        stage('Push to ECR') {
            steps {
                withCredentials([[$class: 'AmazonWebServicesCredentialsBinding', credentialsId: 'aws-ecr-prod-creds']]) {
                    retry(2) {
                        sh '''
                            aws ecr get-login-password --region $AWS_REGION | docker login --username AWS --password-stdin $ECR_REGISTRY
                            docker push $ECR_REGISTRY/$APP_NAME:$IMAGE_TAG
                        '''
                    }
                }
            }
        }

        stage('Manual Approval') {
            when {
                expression { params.DEPLOY_ENV == 'prod' }
            }
            steps {
                timeout(time: 15, unit: 'MINUTES') {
                    input message: "Approve deployment of ${IMAGE_TAG} to PRODUCTION?", ok: 'Approve'
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    // Shared library function encapsulating kubectl/helm logic
                    deployToEKS(
                        appName: env.APP_NAME,
                        image: "${ECR_REGISTRY}/${APP_NAME}:${IMAGE_TAG}",
                        environment: params.DEPLOY_ENV,
                        namespace: params.DEPLOY_ENV
                    )
                }
            }
        }

        stage('Smoke Test') {
            steps {
                sh "./scripts/smoke-test.sh ${params.DEPLOY_ENV}"
            }
        }
    }

    post {
        success {
            slackSend(channel: env.SLACK_CHANNEL, color: 'good',
                message: "✅ ${APP_NAME} ${IMAGE_TAG} deployed to ${params.DEPLOY_ENV} successfully.")
        }
        failure {
            slackSend(channel: env.SLACK_CHANNEL, color: 'danger',
                message: "❌ Build ${env.BUILD_NUMBER} failed for ${APP_NAME}. Check: ${env.BUILD_URL}")
        }
        unstable {
            slackSend(channel: env.SLACK_CHANNEL, color: 'warning',
                message: "⚠️ Build ${env.BUILD_NUMBER} unstable for ${APP_NAME}.")
        }
        always {
            cleanWs()
        }
    }
}
