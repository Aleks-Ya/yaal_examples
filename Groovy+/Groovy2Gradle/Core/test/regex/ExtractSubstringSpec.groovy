package regex

import spock.lang.Specification

class ExtractSubstringSpec extends Specification {

    def "should extract single version from URL"() {
        given:
        def distributionUrl = 'gradle-6.5-bin.zip'

        expect:
        def match = distributionUrl =~ /gradle-(.*)-bin.zip/
        match.find()
        def version = match.group(1)
        version == '6.5'
    }

    def "should extract version from full URL"() {
        given:
        def distributionUrl = 'distributionUrl=https\\://services.gradle.org/distributions/gradle-6.5-bin.zip'

        expect:
        def match = distributionUrl =~ /.*gradle-(.*)-bin.zip/
        match.find()
        def version = match.group(1)
        version == '6.5'
    }

    def "should match multiple substrings"() {
        given:
        def distributionUrl = 'gradle-6.5-bin.zip gradle-7.0-bin.zip'

        when:
        def match = distributionUrl =~ /gradle-([\d.]+)-bin.zip/
        def versions = []
        while (match.find()) {
            def version = match.group(1)
            versions.add(version)
        }

        then:
        versions == ["6.5", "7.0"]
    }
}
