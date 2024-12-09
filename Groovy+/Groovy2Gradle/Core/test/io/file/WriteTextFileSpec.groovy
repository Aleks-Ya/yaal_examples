package io.file

import spock.lang.Specification

class WriteTextFileSpec extends Specification {

    def "should write string to file"() {
        given:
        def content = "abc"
        def file = File.createTempFile(WriteTextFileSpec.simpleName, '.tmp')

        when:
        file.text = content

        then:
        file.text == content
    }
}
