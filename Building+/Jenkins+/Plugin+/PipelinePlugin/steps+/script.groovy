pipeline {
	agent any
	environment {
		MY_ENV_VAR = "hello"
	}
	stages {
	    stage('Condition on an env var') {
	        steps {
	        	script {
                    def myVar
                    if (env.MY_ENV_VAR == 'hello') {
                        myVar = 'world'
                    } else {
                        myVar = 'something_else'
                    }
                    echo "myVar is: ${myVar}"
	        	}
	        }
	    }
	}
}