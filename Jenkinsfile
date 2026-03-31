pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Upload to Nexus') {
            steps {
                nexusArtifactUploader(
                    nexusVersion: 'nexus3',
                    protocol: 'http',
                    nexusUrl: 'YOUR_NEXUS_URL',
                    groupId: 'com',
                    version: '0.0.1-SNAPSHOT',
                    repository: 'maven-snapshots',
                    credentialsId: 'nexus-cred',
                    artifacts: [
                        [
                            artifactId: 'api-set-tracker',
                            classifier: '',
                            file: 'target/api-set-tracker-0.0.1-SNAPSHOT.war',
                            type: 'war'
                        ]
                    ]
                )
            }
        }
    }
}
