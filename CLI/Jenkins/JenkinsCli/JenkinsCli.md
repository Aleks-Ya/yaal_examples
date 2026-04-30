# Jenkins CLI

See all CLI commands: http://localhost:8080/cli

## Setup
1. Setup alias:
```shell
export JENKINS_URL="http://localhost:8080"
wget -O /var/tmp/jenkins-cli.jar "$JENKINS_URL/jnlpJars/jenkins-cli.jar"
alias jenkins="java -jar /var/tmp/jenkins-cli.jar -s $JENKINS_URL"
```
2. Test: `jenkins version`

## Authorization
Login and password authorization

Single command: `jenkins list-jobs --username aleks --password pass`
Many commands:
```shell
jenkins login --username aleks --password pass
jenkins who-am-i
jenkins logout
```

## Restart Jenkins
```shell
jenkins safe-restart
jenkins restart
```

#### Commands
Help: `jenkins help`
Version: `jenkins version`
List jobs: `jenkins list-jobs`

## Job
List jobs: `jenkins list-jobs`
Create a job: `cat freestyle-job-2.xml | jenkins create-job freestyle-job-2`
Get job to file: `jenkins get-job freestyle-job > freestyle-job.xml`
