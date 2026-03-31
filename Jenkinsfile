pipeline {
    agent any

    environment {
        NEXUS_URL = "http://192.168.56.103:8081"
        REPO = "raw-war-backup"
        FILE = "target/api-set-tracker.war"
        CREDS = "admin:admin"   // better use Jenkins credentials in real setup
    }

    stages {

        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Rotate Backups (5 → delete, shift)') {
            steps {
                sh '''
                set -e

                BASE_URL=$NEXUS_URL/repository/$REPO/api-set-tracker

                echo "🔄 Deleting oldest (5)..."
                curl -s -u $CREDS -X DELETE "$BASE_URL/api-tracker-5.war" || true

                echo "🔄 Shifting files..."
                for i in 4 3 2 1
                do
                  NEXT=$((i+1))
                  echo "Moving $i → $NEXT"

                  curl -s -u $CREDS -o temp.war "$BASE_URL/api-tracker-$i.war" || continue

                  curl -s -u $CREDS --upload-file temp.war "$BASE_URL/api-tracker-$NEXT.war"
                done

                rm -f temp.war
                '''
            }
        }

        stage('Upload New as 1') {
            steps {
                sh '''
                BASE_URL=$NEXUS_URL/repository/$REPO/api-set-tracker

                echo "⬆️ Uploading new build as api-tracker-1.war"
                curl -u $CREDS \
                --upload-file $FILE \
                "$BASE_URL/api-tracker-1.war"
                '''
            }
        }
    }
}
