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

    def "parse boolean"() {
        given:
        boolean asBoolean = "true".asBoolean()
        boolean toBoolean1 = "true".toBoolean()
        boolean toBoolean2 = "True".toBoolean()
        boolean toBoolean3 = "".toBoolean()

        expect:
        asBoolean
        toBoolean1
        toBoolean2
        !toBoolean3
    }

}