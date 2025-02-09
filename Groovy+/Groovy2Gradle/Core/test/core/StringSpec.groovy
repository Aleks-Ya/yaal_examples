package core

import spock.lang.Specification

class StringSpec extends Specification {

    def "lower case"() {
        given:
        def str = "Abc"
        def lower = str.toLowerCase()

        expect:
        lower == "abc"
    }


}