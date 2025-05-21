pipeline {
	agent any
	environment {
		BRANCH = "dev"
		TAG = "${BRANCH}-shapshot"
	}
	stages {
	    stage('Show Vars') {
	        steps {
	            echo "BRANCH = ${env.BRANCH}"
	            echo "TAG = ${env.TAG}"
	        }
	    }
	}
}