pipeline {
    agent any

    environment {
        AWS_REGION   = 'us-east-1'
        ECR_REPO     = "<account-id>.dkr.ecr.us-east-1.amazonaws.com/my-app"
        IMAGE_TAG    = "${env.BUILD_NUMBER}"
    }

    parameters {
        choice(name: 'DEPLOY_ENV', choices: ['dev', 'staging', 'prod'], description: 'Target environment')
    }

    options {
        timestamps()
        buildDiscarder(logRotator(numToKeepStr: '20'))
        timeout(time: 30, unit: 'MINUTES')
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'docker build -t $ECR_REPO:$IMAGE_TAG .'
            }
        }

        stage('Test') {
            steps {
                sh 'docker run --rm $ECR_REPO:$IMAGE_TAG npm test'
            }
        }

        stage('Push to ECR') {
            steps {
                withCredentials([[$class: 'AmazonWebServicesCredentialsBinding', credentialsId: 'aws-ecr-creds']]) {
                    sh '''
                        aws ecr get-login-password --region $AWS_REGION | docker login --username AWS --password-stdin $ECR_REPO
                        docker push $ECR_REPO:$IMAGE_TAG
                    '''
                }
            }
        }

        stage('Deploy') {
            when {
                expression { params.DEPLOY_ENV == 'prod' }
            }
            steps {
                input message: "Deploy build $IMAGE_TAG to production?", ok: 'Deploy'
                sh 'kubectl set image deployment/my-app my-app=$ECR_REPO:$IMAGE_TAG --namespace=prod'
            }
        }
    }

    post {
        success {
            echo "Pipeline succeeded for build ${IMAGE_TAG}"
        }
        failure {
            echo "Pipeline failed - check console output"
        }
        always {
            cleanWs()
        }
    }
}
