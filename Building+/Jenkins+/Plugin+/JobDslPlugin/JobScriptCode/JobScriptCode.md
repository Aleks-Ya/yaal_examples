# JobDslPlugin

## Run
1. Copy sources to container: `docker cp src jenkins2:/tmp`
2. Create the seed job:
    ```shell
    wget -O /tmp/jenkins-cli.jar http://localhost:8080/jnlpJars/jenkins-cli.jar
    cat seed-job.xml | java -jar /tmp/jenkins-cli.jar -s http://localhost:8080 create-job seed-job
    ```
3. Build (run) the seed job: `java -jar /tmp/jenkins-cli.jar -s http://localhost:8080 build seed-job`
4. See generated jobs: http://localhost:8080
