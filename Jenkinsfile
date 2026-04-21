pipeline {
    agent any

    parameters {
        booleanParam(name: 'ROLLBACK', defaultValue: false, description: 'Enable rollback from Nexus')
        string(name: 'ROLLBACK_FILE', defaultValue: '', description: 'Enter WAR file name (e.g., api-tracker-2026-04-21_10-30-00.war)')
    }

    environment {
        NEXUS_URL = "http://192.168.56.103:8081"
        REPO = "raw-war-backup"
        FILE = "target/api-set-tracker.war"
        CREDS = "admin:admin"
        BASE_PATH = "api-set-tracker"
    }

    stages {

        stage('Build') {
            when {
                expression { return !params.ROLLBACK }
            }
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Upload with Timestamp') {
            when {
                expression { return !params.ROLLBACK }
            }
            steps {
                sh '''
                set -e

                BASE_URL=$NEXUS_URL/repository/$REPO/$BASE_PATH

                TIMESTAMP=$(date +"%Y-%m-%d_%H-%M-%S")
                FILE_NAME="api-tracker-$TIMESTAMP.war"

                echo "⬆️ Uploading $FILE_NAME"

                curl -s -u $CREDS \
                --upload-file $FILE \
                "$BASE_URL/$FILE_NAME"

                echo $FILE_NAME > latest_uploaded.txt
                '''
            }
        }

        stage('Cleanup Old Backups (Keep Last 5)') {
            when {
                expression { return !params.ROLLBACK }
            }
            steps {
                sh '''
                set -e

                BASE_URL=$NEXUS_URL/repository/$REPO/$BASE_PATH

                FILE_LIST=$(curl -s -u $CREDS "$BASE_URL/" | \
                    grep -o 'api-tracker-[^"]*\\.war' | \
                    sort)

                COUNT=$(echo "$FILE_LIST" | wc -l)

                if [ "$COUNT" -gt 5 ]; then
                    REMOVE_COUNT=$((COUNT - 5))

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

        stage('Download Rollback Artifact') {
            when {
                expression { return params.ROLLBACK }
            }
            steps {
                sh '''
                set -e

                if [ -z "$ROLLBACK_FILE" ]; then
                    echo "❌ ROLLBACK_FILE is required!"
                    exit 1
                fi

                BASE_URL=$NEXUS_URL/repository/$REPO/$BASE_PATH

                echo "⬇️ Downloading $ROLLBACK_FILE"

                curl -f -u $CREDS \
                -o rollback.war \
                "$BASE_URL/$ROLLBACK_FILE"
                '''
            }
        }

        stage('Run Application') {
            steps {
                sh '''
                set -e

                # Decide which WAR to run
                if [ "$ROLLBACK" = "true" ]; then
                    WAR_FILE="rollback.war"
                else
                    WAR_FILE=$FILE
                fi

                echo "🛑 Stopping existing app..."
                PID=$(pgrep -f "$WAR_FILE" || true)

                if [ ! -z "$PID" ]; then
                    kill $PID || true
                fi

                echo "🚀 Starting application..."

                nohup java -jar $WAR_FILE > app.log 2>&1 &

                sleep 5

                echo "✅ Application started"
                echo "🌐 http://localhost:8080"
            '''
            }
        }
    }
}
