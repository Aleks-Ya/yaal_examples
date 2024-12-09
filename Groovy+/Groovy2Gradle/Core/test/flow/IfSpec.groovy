package flow

import spock.lang.Specification

class IfSpec extends Specification {
    def "ternary"() {
        expect:
        (1 > 2 ? 'abc' : 'xyz') == 'xyz'
    }

    def "if-else-if-else"() {
        when:
        def result
        def value = 10

        if (value > 20) {
            result = 'greater than 20'
        } else if (value > 15) {
            result = 'greater than 15'
        } else if (value > 10) {
            result = 'greater than 10'
        } else {
            result = '10 or less'
        }

        then:
        result == '10 or less'
    }
}
