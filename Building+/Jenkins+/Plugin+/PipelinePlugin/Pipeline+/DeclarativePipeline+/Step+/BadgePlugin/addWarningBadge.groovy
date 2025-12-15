package badge

pipeline {
    agent any
    stages {
        stage('Hello') {
            steps {
                addWarningBadge text: "BUILD_ID=${env.BUILD_ID}, Build started at ${new Date()}"
                echo "Hello, World!"
            }
        }
    }
}