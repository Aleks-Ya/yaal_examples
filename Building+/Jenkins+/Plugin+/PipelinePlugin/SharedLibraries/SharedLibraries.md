# Shared Variables in Pipeline Plugin

## Test
Run unit-tests: `./gradlew test`

## Run
1. Start container:
    1. Start Jenkins in Docker (e.g. `Building+/Jenkins+/JenkinsDocker+/Jenkins_v2+/Jenkins_2.x`)
    2. Copy sources to container: `./copy_to_container.sh`
2. Add a shared library:
    1. Go to `Manage Jenkins` -> `Configure System` -> `Global Untrusted Pipeline Libraries`
    2. Add a library:
        1. Name: `custom`
        2. Default version: `master`
        3. Project Repository: `file:///tmp/shared-libraries-git-repo`
3. Create a job:
    1. Create a job of type `Pipeline`
    2. Open Configuration of the job
    3. Insert script from file `job.groovy` into `Script` field
4. Run the job:
    1. Run the job
    2. Find in console output: `The string: ABC`
