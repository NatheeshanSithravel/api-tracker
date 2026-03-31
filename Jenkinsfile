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
                    nexusUrl: '192.168.56.103.8081',
                    groupId: 'com',
                    version: '0.0.1-SNAPSHOT',
                    repository: 'maven-snapshots',
                    credentialsId: 'nexus-cred',
                    artifacts: [
                         [
                    artifactId: 'api-set-tracker',
                    classifier: '',
                    file: 'target/api-set-tracker.war',
                    type: 'war'
                        ]
                    ]
                )
            }
        }
    }
}
