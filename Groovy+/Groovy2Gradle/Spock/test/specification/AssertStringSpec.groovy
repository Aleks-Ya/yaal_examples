package specification

import spock.lang.Specification

import java.util.regex.Pattern

class AssertStringSpec extends Specification {

    def "string is equal"() {
        given:
        String str = "a"

        expect:
        str == "a"
        str != "A"
    }

    def "string contains"() {
        given:
        String str = "aaa bbb ccc"

        expect:
        str.contains("bbb")
        str =~ /${Pattern.quote("bbb")}/
        str =~ /bbb/
        str ==~ /.*bbb.*/

        !str.contains("zzz")
    }

}
