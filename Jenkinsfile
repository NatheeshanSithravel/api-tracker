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
                set -e

                BASE_URL=$NEXUS_URL/repository/$REPO/api-set-tracker

                # Generate timestamp
                TIMESTAMP=$(date +"%Y-%m-%d_%H-%M-%S")
                FILE_NAME="api-tracker-$TIMESTAMP.war"

                echo "⬆️ Uploading $FILE_NAME"

                curl -s -u $CREDS \
                --upload-file $FILE \
                "$BASE_URL/$FILE_NAME"
                '''
            }
        }

        stage('Cleanup Old Backups (Keep Last 5)') {
            steps {
                sh '''
                set -e

                BASE_URL=$NEXUS_URL/repository/$REPO/api-set-tracker

                echo "🧹 Fetching file list..."

                # Get file list (HTML) → extract .war names
                FILE_LIST=$(curl -s -u $CREDS "$BASE_URL/" | \
                    grep -o 'api-tracker-[^"]*\\.war' | \
                    sort)

                echo "📦 All files:"
                echo "$FILE_LIST"

                # Count files
                COUNT=$(echo "$FILE_LIST" | wc -l)

                echo "Total files: $COUNT"

                if [ "$COUNT" -gt 5 ]; then
                    REMOVE_COUNT=$((COUNT - 5))

                    echo "🗑️ Removing $REMOVE_COUNT old files..."

                    echo "$FILE_LIST" | head -n $REMOVE_COUNT | while read FILE
                    do
                        echo "Deleting $FILE"
                        curl -s -u $CREDS -X DELETE "$BASE_URL/$FILE"
                    done
                else
                    echo "✅ No cleanup needed"
                fi
                '''
            }
        }
    }
}
