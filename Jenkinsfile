pipeline {
    agent any

    environment {
        NEXUS_URL = "http://192.168.56.103:8081"
        REPO = "raw-war-backup"
        FILE = "target/api-set-tracker.war"
        CREDS = "admin:admin"
    }

    stages {

        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Upload with Timestamp') {
            steps {
                sh '''
                BASE_URL=$NEXUS_URL/repository/$REPO/api-set-tracker

                # Generate timestamp
                TIMESTAMP=$(date +"%Y-%m-%d_%H-%M-%S")

                FILE_NAME="api-tracker-$TIMESTAMP.war"

                echo "⬆️ Uploading $FILE_NAME"

                curl -u $CREDS \
                --upload-file $FILE \
                "$BASE_URL/$FILE_NAME"
                '''
            }
        }
    }
}
