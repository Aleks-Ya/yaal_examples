pipeline {
    agent any
    stages {
        stage('Hello') {
            steps {
                script {
                    currentBuild.description = "BUILD_ID=${env.BUILD_ID}, Build started at ${new Date()}"
                }
                echo "Hello, World!"
            }
        }
    }
}