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
                sh 'mvn clean package'
            }
        }

        stage('Deploy') {
            steps {
                echo 'Deploying on wildfly server...'
                sh 'cp target/api-set-tracker.war /opt/wildfly/standalone/deployments/'
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
