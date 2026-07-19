def services = ['auth-service', 'billing-service', 'notification-service']

node('linux-docker') {

    stage('Checkout') {
        checkout scm
    }

    stage('Build All Services') {
        def buildTasks = [:]
        for (svc in services) {
            def serviceName = svc
            buildTasks[serviceName] = {
                dir(serviceName) {
                    sh "docker build -t ${serviceName}:${env.BUILD_NUMBER} ."
                }
            }
        }
        parallel buildTasks
    }

    stage('Test All Services') {
        for (svc in services) {
            try {
                dir(svc) {
                    sh 'npm test'
                }
            } catch (err) {
                echo "Tests failed for ${svc}: ${err}"
                currentBuild.result = 'UNSTABLE'
            }
        }
    }

    stage('Push Images') {
        withCredentials([[$class: 'AmazonWebServicesCredentialsBinding', credentialsId: 'aws-ecr-creds']]) {
            sh 'aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin <account-id>.dkr.ecr.us-east-1.amazonaws.com'
            for (svc in services) {
                sh "docker tag ${svc}:${env.BUILD_NUMBER} <account-id>.dkr.ecr.us-east-1.amazonaws.com/${svc}:${env.BUILD_NUMBER}"
                sh "docker push <account-id>.dkr.ecr.us-east-1.amazonaws.com/${svc}:${env.BUILD_NUMBER}"
            }
        }
    }

    stage('Deploy') {
        if (env.BRANCH_NAME == 'main') {
            for (svc in services) {
                sh "kubectl set image deployment/${svc} ${svc}=<account-id>.dkr.ecr.us-east-1.amazonaws.com/${svc}:${env.BUILD_NUMBER} --namespace=prod"
            }
        } else {
            echo "Skipping deploy - not on main branch"
        }
    }
}
