package collection


import spock.lang.Specification

class ArraySpec extends Specification {

    def "empty array"() {
        expect:
        def a = [] as String[]
        a.length == 0
    }

    def "integer array"() {
        given:
        def numbers = [1, 2, 3, 4, 5] as Integer[]

        expect:
        def firstElement = numbers[0]
        firstElement == 1
    }

    def "iterate an array"() {
        given:
        def numbers = [1, 2, 3] as Integer[]

        when:
        def output = []
        numbers.each { output << it * 2 }

        then:
        output == [2, 4, 6]
    }


}