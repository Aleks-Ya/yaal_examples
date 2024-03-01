# Jenkins CLI

See CLI commands: http://localhost:8080/cli

## Prepare
```
export JENKINS_URL=http://localhost:8080
wget $JENKINS_URL/jnlpJars/jenkins-cli.jar
export JCLI="java -jar jenkins-cli.jar"

$JCLI help
```

## Authorization
Login and password authorization

Single command: `java -jar jenkins-cli.jar -s $JENKINS_URL list-jobs --username aleks --password pass`
Many commands:
```
$JCLI login --username aleks --password pass
$JCLI who-am-i
$JCLI logout
```

## Restart Jenkins
```
$JCLI safe-restart
$JCLI restart
```

## Job
List jobs: `$JCLI list-jobs`
Create a job: `cat freestyle-job-2.xml | $JCLI create-job freestyle-job-2`
Get job to file: `$JCLI get-job freestyle-job > freestyle-job.xml`
