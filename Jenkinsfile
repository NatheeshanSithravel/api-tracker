pipeline {
    agent any
    tools{
        jdk 'JDK_21'
        maven 'Maven 3.9.9'
    
    }
    environment {
        BUILD_VERSION = "1.0.${BUILD_NUMBER}"
    }

    stages {

        stage('Build') {
            steps {
                echo "Building version ${BUILD_VERSION}" 
                sh 'mvn clean install'
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
