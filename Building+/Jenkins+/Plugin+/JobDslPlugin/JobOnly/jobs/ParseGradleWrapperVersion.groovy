String basePath = 'JobOnly'

folder(basePath) {
    description 'This example shows basic folder/job creation.'
}

def scriptName = getClass().getSimpleName().replace('.groovy', '')
job("$basePath/$scriptName") {
    steps {
        def gradleVersion = getGradleWrapperVersion()
        shell "echo 'Gradle Wrapper version: $gradleVersion'"
    }
}

private Optional<String> getGradleWrapperVersion() {
    def version = Optional.empty()
    def propertiesFile = 'gradle/wrapper/gradle-wrapper.properties'
    try {
        def content = readFileFromWorkspace(propertiesFile)
        def match = content =~ /.*gradle-(.*)-bin.zip/
        if (match.find()) {
            version = Optional.of(match.group(1))
        }
    } catch (Exception e) {
        println("Cannot parse $propertiesFile: $e")
    }
    println("Gradle Wrapper version: $version")
    return version
}
