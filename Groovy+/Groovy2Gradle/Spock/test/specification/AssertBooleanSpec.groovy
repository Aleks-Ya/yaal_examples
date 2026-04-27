package specification

import spock.lang.Specification

class AssertBooleanSpec extends Specification {

    def "assert boolean"() {
        given:
        def a = true
        def b = false

        expect:
        a == true
        a
        b == false
        !b
    }

}
