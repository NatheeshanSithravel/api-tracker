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
                    groupId: 'com.example',
                    version: '1.0.0',
                    repository: 'maven-releases',
                    credentialsId: 'nexus-cred',
                    artifacts: [
                        [
                            artifactId: 'my-app',
                            classifier: '',
                            file: 'target/my-app.jar',
                            type: 'jar'
                        ]
                    ]
                )
            }
        }
    }
}
