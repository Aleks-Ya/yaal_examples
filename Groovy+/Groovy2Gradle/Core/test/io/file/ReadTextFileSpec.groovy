package io.file

import spock.lang.Specification

class ReadTextFileSpec extends Specification {

    def "fileToString"() {
        given:
        def path = getClass().getResource('/io/file/data.txt').path

        when:
        def content = new File(path).text

        then:
        content == 'abc\nefg'
    }

    def "fileToLineList"() {
        given:
        def path = getClass().getResource('/io/file/data.txt').path

        when:
        def lines = new File(path).readLines()

        then:
        lines.size() == 2
        lines == ['abc', 'efg']
    }

    def "emptyFileToString"() {
        given:
        def path = getClass().getResource('/io/file/empty.txt').path

        when:
        def content = new File(path).text

        then:
        content.isEmpty()
    }
}
