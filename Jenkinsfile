properties([
  parameters([
    booleanParam(
      name: 'ROLLBACK',
      defaultValue: false,
      description: 'Enable rollback from Nexus'
    ),

    [$class: 'CascadeChoiceParameter',
      choiceType: 'PT_SINGLE_SELECT',
      name: 'ROLLBACK_FILE',
      description: 'Select backup file from Nexus',
      referencedParameters: 'ROLLBACK',
      script: [
        $class: 'GroovyScript',
        script: [
          sandbox: true,
          script: '''
            if (!ROLLBACK) {
                return ["-- Rollback disabled --"]
            }

            def nexusUrl = "http://192.168.56.103:8081/#browse/browse:raw-war-backup:api-set-tracker/"
            def user = "admin"
            def pass = "admin"

            def connection = new URL(nexusUrl).openConnection()
            String basicAuth = user + ":" + pass
            String encoded = basicAuth.bytes.encodeBase64().toString()
            connection.setRequestProperty("Authorization", "Basic " + encoded)

            def html = connection.inputStream.text

            def files = []
            html.eachMatch(/api-tracker-[^"]+\\.war/) { match ->
                files << match
            }

            return files.unique().sort().reverse()
          '''
        ]
      ]
    ]
  ])
])

pipeline {
    agent any

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

        stage('Download Rollback Artifact') {
            when {
                expression { return params.ROLLBACK }
            }
            steps {
                sh '''
                set -e

                if [ "$ROLLBACK_FILE" = "-- Rollback disabled --" ] || [ -z "$ROLLBACK_FILE" ]; then
                    echo "❌ Please select a valid rollback file"
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
                echo "📄 Logs: app.log"
            '''
            }
        }
    }
}
