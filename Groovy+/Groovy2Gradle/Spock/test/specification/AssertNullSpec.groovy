package specification

import spock.lang.Specification

class AssertNullSpec extends Specification {

    def "assert null"() {
        given:
        String a = "A"
        String b = null

        expect:
        a != null
        !a.is(null)
        b == null
        b.is(null)
    }

}
