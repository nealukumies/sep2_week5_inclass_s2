pipeline {
    agent any
    tools {
        maven 'MAVEN_HOME'
    }

    environment {
     PATH = "C:\\Program Files\\Docker\\Docker\\resources\\bin;${env.PATH}"
     JAVA_HOME = 'C:\\Java\\jdk-21'  // Adjust to your actual JDK pat
     SONARQUBE_SERVER = 'SonarQubeServer'  // The name of the SonarQube server configured in Jenkins
     SONAR_TOKEN = 'sqa_f5174ce6656f712d46b143718985ab22f30d0c96' // Store the token securely
     DOCKERHUB_CREDENTIALS_ID = 'Docker_Hub'
     DOCKERHUB_REPO = 'oonnea/otp2_devops_demo'
     DOCKER_IMAGE_TAG = 'latest'


    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/nealukumies/sep2_week5_inclass_s2.git'
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean install'
            }
        }

        stage('SonarQube Analysis') {
                    steps {
                        withSonarQubeEnv('SonarQubeServer') {
                            bat """
                                ${tool 'SonarScanner'}\\bin\\sonar-scanner ^
                                -Dsonar.projectKey=devops-demo ^
                                -Dsonar.sources=src ^
                                -Dsonar.projectName=DevOps-Demo ^
                                -Dsonar.host.url=http://localhost:9000 ^
                                -Dsonar.login=${env.SONAR_TOKEN} ^
                                -Dsonar.java.binaries=target/classes
                            """
                        }
                    }
                }



        stage('Build Docker Image') {
                    steps {
                        script {
                            docker.build("${DOCKERHUB_REPO}:${DOCKER_IMAGE_TAG}")
                            // Or specify Dockerfile path explicitly if needed
                            // docker.build("${DOCKERHUB_REPO}:${DOCKER_IMAGE_TAG}", "-f ./Dockerfile .")
                        }
                    }
                }

                stage('Push Docker Image to Docker Hub') {
                    steps {
                        script {
                            docker.withRegistry('https://index.docker.io/v1/', DOCKERHUB_CREDENTIALS_ID) {
                                docker.image("${DOCKERHUB_REPO}:${DOCKER_IMAGE_TAG}").push()
                            }
                        }
                    }
                }
    }
}
