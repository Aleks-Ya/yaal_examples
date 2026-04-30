pipeline {
	agent any
	stages {
		stage('Read a JSON string') {
			steps {
				script {
					def json = readJSON(text: '{"city":"London"}')
					def city = json.city
					echo "City: $city"
				}
			}
		}
	    stage('Read a JSON file') {
	        steps {
	        	writeFile(file: 'data.json', text: '{"city":"London"}')
				script {
					def json = readJSON(file: "data.json")
					def city = json.city
					echo "City: $city"
				}
	        }
	    }
	}
}