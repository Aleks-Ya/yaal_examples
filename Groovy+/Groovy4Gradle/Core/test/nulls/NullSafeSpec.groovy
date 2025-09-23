package nulls


import spock.lang.Specification

class NullSafeSpec extends Specification {

    def "Safe Navigation Operator"() {
        setup:
        def person = null
        def name = person?.name

        expect:
        name == null
    }

    def "Elvis Operator"() {
        setup:
        def username = null
        def displayName = username ?: 'Guest'

        expect:
        displayName == 'Guest'
    }

    def "Null-Safe Indexing"() {
        setup:
        def list = null
        def value = list?[0]

        expect:
        value == null
    }

    def "Null-Safe Method Calls"() {
        setup:
        def text = null
        def length = text?.toUpperCase()?.length()

        expect:
        length == null
    }

    def "asBoolean"() {
        setup:
        def value = null
        String symbol = null
        if (value) {
            symbol = "a"
        } else {
            symbol = "b"
        }

        expect:
        symbol == "b"
    }

}