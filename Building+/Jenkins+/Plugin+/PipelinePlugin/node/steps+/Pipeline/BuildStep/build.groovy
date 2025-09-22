node {
    stage('Trigger Downstream Job') {
        build job: 'echo-job'
    }
}