package specification

import spock.lang.Ignore
import spock.lang.Specification

class IgnoreSpec extends Specification {

    def "not ignored test"() {
        expect:
        true
    }

    @Ignore
    def "ignored test"() {
        expect:
        fail("Should be ignored")
    }

    @Ignore("Because")
    def "ignored test with comment"() {
        expect:
        fail("Should be ignored")
    }
}
