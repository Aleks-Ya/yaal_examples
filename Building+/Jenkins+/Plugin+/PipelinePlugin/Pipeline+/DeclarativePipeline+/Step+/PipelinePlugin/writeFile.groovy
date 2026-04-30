pipeline {
	agent any
	stages {
	    stage('Write a local file') {
	        steps {
	        	writeFile (file: 'data.tmp', text: 'abc')
	        	sh "cat data.tmp"
	        }
	    }
	}
}