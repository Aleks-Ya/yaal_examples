# JobDslPlugin (jobs only)

## Run

1. Copy sources to container: `./copy_to_container.sh`
2. Create the seed job:
    ```
    wget -O /tmp/jenkins-cli.jar http://localhost:8080/jnlpJars/jenkins-cli.jar
    cat seed-job.xml | java -jar /tmp/jenkins-cli.jar -s http://localhost:8080 create-job seed-job
    ```
3. Build (run) the seed job: `java -jar /tmp/jenkins-cli.jar -s http://localhost:8080 build jobs-only-seed-job`
4. See generated jobs: http://localhost:8080

Save the seed job into an XML file:
`java -jar /tmp/jenkins-cli.jar -s http://localhost:8080 get-job jobs-only-seed-job > jobs-only-seed-job.xml`
