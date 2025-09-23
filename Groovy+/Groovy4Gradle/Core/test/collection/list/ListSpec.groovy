package collection.list


import spock.lang.Specification

class ListSpec extends Specification {

    def "declare list"() {
        setup:
        def list1 = ["A", "B"]

        expect:
        list1.class == ArrayList
    }

    def "modify list elements"() {
        setup:
        def numbers = [10, 5, 15]
        def twiceNumbers = numbers.collect { it * 2 }

        expect:
        twiceNumbers.class == ArrayList
        twiceNumbers == [20, 10, 30]
    }

}