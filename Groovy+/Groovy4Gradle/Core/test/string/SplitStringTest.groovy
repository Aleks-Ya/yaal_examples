package string


import spock.lang.Specification

class SplitStringSpec extends Specification {
    def "split a string"() {
        when:
        def split1 = 'abc=100'.split('=')
        def split2 = 'abc='.split('=')
        def split3 = 'abc'.split('=')

        then:
        split1 == ['abc', '100'] as String[]
        split2 == ['abc'] as String[]
        split3 == ['abc'] as String[]
    }
}
