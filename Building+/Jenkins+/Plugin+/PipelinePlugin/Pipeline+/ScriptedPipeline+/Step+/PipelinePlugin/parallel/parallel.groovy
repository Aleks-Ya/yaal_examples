node {
    stage('Parallel Example') {
        parallel(
                "Branch A": {
                    echo "Running Branch A"
                },
                "Branch B": {
                    echo "Running Branch B"
                }
        )
    }
}