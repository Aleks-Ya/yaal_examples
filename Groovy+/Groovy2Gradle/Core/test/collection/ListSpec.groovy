package collection

import spock.lang.Ignore
import spock.lang.Specification

class ListSpec extends Specification {

    def "empty list"() {
        expect:
        def numbers = []
        numbers.size() == 0
    }

    def "integer list"() {
        given:
        def numbers = [1, 2, 3, 4, 5]

        expect:
        def firstElement = numbers[0]
        firstElement == 1
    }

    def "add element"() {
        given:
        def numbers = [1, 2]

        expect:
        numbers == [1, 2]

        when:
        numbers.add(3)
        numbers.add(0, 0)
        numbers.add(4)

        then:
        numbers == [0, 1, 2, 3, 4]
    }

    def "test filter list"() {
        given:
        def numbers = [1, -2, 0, 4, -5]

        when:
        def positive = numbers.findAll { it > 0 }

        then:
        positive == [1, 4]
    }

    def "map list"() {
        given:
        def numbers = [1, -2, 0]

        when:
        def doubledNumbers = numbers.collect { it * 2 }

        then:
        doubledNumbers == [2, -4, 0]
    }

    def "each"() {
        given:
        def numbers = [1, -2, 0]

        when:
        def output = []
        numbers.each { output << it }

        then:
        output == [1, -2, 0]
    }

    def "forEach"() {
        given:
        def numbers = [1, -2, 0]

        when:
        def output = []
        numbers.forEach { output << it }

        then:
        output == [1, -2, 0]
    }

    def "list to map (one line)"() {
        given:
        def numbers = [1, -2, 0]

        when:
        def numbersMap = numbers.collectEntries { [(it): it * 2] }

        then:
        numbersMap == [(1): 2, (-2): -4, (0): 0]
    }

    def "list to map (ternary)"() {
        given:
        def numbers = [1, -2, 0]

        when:
        def numbersMap = numbers
                .collectEntries { [(it): (it > 0 ? "Positive" : (it == 0 ? "Zero" : "Negative"))] }

        then:
        numbersMap == [(1): "Positive", (-2): "Negative", (0): "Zero"]
    }

    def "list to map (if key-value)"() {
        given:
        def numbers = [1, -2, 0]

        when:
        def numbersMap = numbers.collectEntries {
            if (it > 0) {
                [(it): "Positive"]
            } else if (it < 0) {
                [(it): "Negative"]
            } else {
                [(it): "Zero"]
            }
        }

        then:
        numbersMap == [(1): "Positive", (-2): "Negative", (0): "Zero"]
    }

    @Ignore("does not work")
    def "list to map (if value)"() {
        given:
        def numbers = [1, -2, 0]

        when:
        def numbersMap = numbers.collectEntries {
            [(it): {
                if (it > 0) {
                    "Positive"
                } else if (it < 0) {
                    "Negative"
                } else {
                    "Zero"
                }
            }]
        }

        then:
        numbersMap == [(1): "Positive", (-2): "Negative", (0): "Zero"]
    }

    def "clone list"() {
        given:
        List<Integer> numbers = [1, 2, 3]
        List<Integer> copy = numbers.clone()

        when:
        copy[1] = 100

        then:
        assert numbers == [1, 2, 3]
        assert copy == [1, 100, 3]
    }

    def "get list elements not in set"() {
        given:
        List<Integer> list = [1, 2, 3, 4, 5]
        Set<Integer> set = [2, 4] as Set

        when:
        List<Integer> result = list - set

        then:
        assert result == [1, 3, 5]
    }
}