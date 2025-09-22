def tasks = [
        "Task A": { echo 'Running Task A' },
        "Task B": { echo 'Running Task B' }
]

node {
    stage('Parallel Tasks') {
        parallel tasks
    }
}