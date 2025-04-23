pipeline {
    agent any

    environment {
        BUILD_VERSION = "1.0.${BUILD_NUMBER}"
    }

    stages {
        stage('Clone Repository') {
            steps {
                // Clone from GitHub using Jenkins credentials
                git credentialsId: 'github-credentials-id', url: 'https://github.com/NatheeshanSithravel/api-tracker.git'
            }
        }

        stage('Build') {
            steps {
                echo "Building version ${BUILD_VERSION}"
                sh 'mvn clean package'
            }
        }

        stage('Test') {
            steps {
                echo 'Running unit tests...'
                sh 'mvn test'
            }
        }

        stage('Archive Artifacts') {
            steps {
                archiveArtifacts artifacts: 'target/*.war', fingerprint: true
            }
        }
    }

    post {
        success {
            echo '✅ Build completed successfully!'
        }
        failure {
            echo '❌ Build failed.'
        }
    }
}
