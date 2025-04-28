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
                sh '/opt/wildfly/bin/./jboss-cli.sh --connect --command=" deploy /var/lib/jenkins/workspace/bitbucket/target/api-set-tracker.war" '
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
