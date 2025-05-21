pipeline {
	agent any
	environment {
		SNAPSHOT_KEYWORD = 'snapshot'
		BRANCH = "dev"
		VERSION = "${env.BRANCH == 'dev' ? env.SNAPSHOT_KEYWORD : 'release'}"
		TAG = "${BRANCH}-${VERSION}"
		FEATURE_BRANCH = "JIRA-1234_learn-Jenkins"
		REVISION = """${env.FEATURE_BRANCH.replaceAll('[^a-zA-Z0-9]','_') + '-' + env.BUILD_ID}"""
	}
	stages {
	    stage('Show Vars') {
	        steps {
	        	echo "BUILD_ID = ${env.BUILD_ID}"
	            echo "BRANCH = ${env.BRANCH}"
	            echo "VERSION = ${env.VERSION}"
	            echo "TAG = ${env.TAG}"
	            echo "FEATURE_BRANCH = ${env.FEATURE_BRANCH}"
	            echo "REVISION = ${env.REVISION}"
	        }
	    }
	}
}