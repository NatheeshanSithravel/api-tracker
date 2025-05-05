pipeline {
    agent any
    tools{
        jdk 'jdk-21'
    }
    environment {
        BUILD_VERSION = "1.0.${BUILD_NUMBER}"
    }

    stages {

        stage('Build') {
            steps {
                echo "Building version ${BUILD_VERSION}" 
                sh 'java --version'
            }
        }
    }

    post {
        success {
            echo '✅ Build completed-uh successfully!'
        }
        failure {
            echo '❌ Build failed-uh'
        }
    }
}
