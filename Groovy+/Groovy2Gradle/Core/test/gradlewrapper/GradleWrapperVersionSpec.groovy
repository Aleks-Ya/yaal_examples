package gradlewrapper

import spock.lang.Specification

import java.nio.file.Files
import java.nio.file.Paths

class GradleWrapperVersionSpec extends Specification {

    def "success1"() {
        when:
        def dir = getProjectDir('/app/gradlewrapper/success1/gradle/wrapper/gradle-wrapper.properties')
        def version = parseGradleWrapperVersion(dir)

        then:
        version.get() == '7.1'
    }

    def "success2"() {
        when:
        def dir = getProjectDir('/app/gradlewrapper/success2/gradle/wrapper/gradle-wrapper.properties')
        def version = parseGradleWrapperVersion(dir)

        then:
        version.get() == '6.7.9'
    }

    def "noVersion"() {
        when:
        def dir = getProjectDir('/app/gradlewrapper/noversion/gradle/wrapper/gradle-wrapper.properties')
        def version = parseGradleWrapperVersion(dir)

        then:
        !version.isPresent()
    }

    def "noFile"() {
        when:
        def dir = '/tmp/absent/gradle/wrapper/gradle-wrapper.properties'
        def version = parseGradleWrapperVersion(dir)

        then:
        !version.isPresent()
    }

    def "nullDir"() {
        when:
        def version = parseGradleWrapperVersion(null)

        then:
        !version.isPresent()
    }

    def "emptyDir"() {
        when:
        def version = parseGradleWrapperVersion('')

        then:
        !version.isPresent()
    }

    private def getProjectDir(String gradleWrapperProperties) {
        new File(getClass().getResource(gradleWrapperProperties).file).parentFile.parentFile.parentFile.path
    }

    private static Optional<String> parseGradleWrapperVersion(String projectRootDir) {
        if (projectRootDir != null) {
            def file = Paths.get(projectRootDir, "gradle", "wrapper", "gradle-wrapper.properties")
            if (Files.exists(file)) {
                def match = file.text =~ /.*gradle-(.*)-bin.zip/
                if (match.find()) {
                    return Optional.of(match.group(1))
                }
            }
        }
        return Optional.empty()
    }
}
