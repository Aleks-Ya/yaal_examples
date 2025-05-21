pipeline {
	agent any
	environment {
		SNAPSHOT_KEYWORD = 'snapshot'
		BRANCH = "dev"
		VERSION = "${env.BRANCH == 'dev' ? env.SNAPSHOT_KEYWORD : 'release'}"
		TAG = "${BRANCH}-${VERSION}"
		REVISION = """${env.BRANCH}"""
	}
	stages {
	    stage('Show Vars') {
	        steps {
	            echo "BRANCH = ${env.BRANCH}"
	            echo "BRANCH = ${env.VERSION}"
	            echo "TAG = ${env.TAG}"
	            echo "REVISION = ${env.REVISION}"
	        }
	    }
	}
}