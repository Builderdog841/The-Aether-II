pipeline {
    agent {
        docker {
            image 'gradle:4.10.3-jdk8-alpine'
            args '-v gradle-cache:/home/gradle/.gradle'
        }
    }

    stages {
        stage('Clean') {
            steps {
                dir('build/libs') {
                    deleteDir()
                }
            }
        }

        stage('Build') {
            steps {
                sh 'gradle ciBuild'
            }
        }
    }

    post {
        success {
            archiveArtifacts artifacts: 'build/libs/*.jar', fingerprint: true
        }
    }
}