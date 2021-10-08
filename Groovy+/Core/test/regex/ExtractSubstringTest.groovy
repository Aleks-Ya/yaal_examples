package regex

import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.fail

class ExtractSubstringTest {

    @Test
    void substring() {
        def distributionUrl = 'gradle-6.5-bin.zip'
        def match = (distributionUrl =~ /gradle-(.*)-bin.zip/)
        if (match.find()) {
            def version = match.group(1)
            assertEquals('6.5', version)
        } else {
            fail()
        }
    }

    @Test
    void gradleVersion() {
        def distributionUrl = 'distributionUrl=https\\://services.gradle.org/distributions/gradle-6.5-bin.zip'
        def match = (distributionUrl =~ /.*gradle-(.*)-bin.zip/)
        if (match.find()) {
            def version = match.group(1)
            assertEquals('6.5', version)
        } else {
            fail()
        }
    }

}
