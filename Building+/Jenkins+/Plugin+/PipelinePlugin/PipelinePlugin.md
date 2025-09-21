# Jenkins Pipeline plugin

Sources: https://github.com/jenkinsci/pipeline-plugin
Docs: https://www.jenkins.io/doc/book/pipeline

Run:
1. Start Jenkins in Docker (e.g. `Building+/Jenkins+/JenkinsDocker+/Jenkins_v2+/Jenkins_2.516.3`)
2. Create a job of type `Pipeline`
3. Open Configuration of the job
4. Insert script from file `environment.groovy` into `Script` field
5. Run the job
6. See console output
