package gradlewrapper

import org.junit.jupiter.api.Test

import java.nio.file.Files
import java.nio.file.Paths

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertFalse

class GradleWrapperVersionTest {

    @Test
    void success1() {
        def dir = getProjectDir('/app/gradlewrapper/success1/gradle/wrapper/gradle-wrapper.properties')
        def version = parseGradleWrapperVersion(dir)
        assertEquals('7.1', version.get())
    }

    @Test
    void success2() {
        def dir = getProjectDir('/app/gradlewrapper/success2/gradle/wrapper/gradle-wrapper.properties')
        def version = parseGradleWrapperVersion(dir)
        assertEquals('6.7.9', version.get())
    }

    @Test
    void noVersion() {
        def dir = getProjectDir('/app/gradlewrapper/noversion/gradle/wrapper/gradle-wrapper.properties')
        def version = parseGradleWrapperVersion(dir)
        assertFalse(version.isPresent())
    }

    @Test
    void noFile() {
        def dir = '/tmp/absent/gradle/wrapper/gradle-wrapper.properties'
        def version = parseGradleWrapperVersion(dir)
        assertFalse(version.isPresent())
    }

    @Test
    void nullDir() {
        def version = parseGradleWrapperVersion(null)
        assertFalse(version.isPresent())
    }

    @Test
    void emptyDir() {
        def version = parseGradleWrapperVersion('')
        assertFalse(version.isPresent())
    }

    private def getProjectDir(String gradleWrapperProperties) {
        new File(getClass().getResource(gradleWrapperProperties).file).parentFile.parentFile.parentFile.path
    }

    /**
     * Parse Gradle version from "gradle/wrapper/gradle-wrapper.properties".
     * @param projectRootDir Example: for "/my/project/gradle/wrapper/gradle-wrapper.properties" should be "/my/project".
     * @return Gradle version if found.
     */
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
