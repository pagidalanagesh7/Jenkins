/*
---------------------------------------------------------
Declarative Jenkins Pipeline
Jenkins Learning Series - Day 4
Topic: Kubernetes Authentication using kubeconfig
---------------------------------------------------------
*/

pipeline {

    agent any

    stages {

        stage('Check Kubernetes Cluster') {

            steps {

                withCredentials([
                    file(
                        credentialsId: 'kubeconfig',
                        variable: 'KUBECONFIG'
                    )
                ]) {

                    sh 'kubectl get nodes'

                }

            }

        }

    }

}