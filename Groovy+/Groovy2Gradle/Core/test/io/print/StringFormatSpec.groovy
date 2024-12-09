package io.print

import spock.lang.Specification

class StringFormatSpec extends Specification {

    def "format test"() {
        given:
        def template = 'Size is %d'

        when:
        def formatted = String.format(template, 42)

        then:
        formatted == "Size is 42"
    }

    def "gstring test"() {
        given:
        def size = 42

        when:
        def formatted = "Size is $size"

        then:
        formatted.toString() == "Size is 42"
    }
}
