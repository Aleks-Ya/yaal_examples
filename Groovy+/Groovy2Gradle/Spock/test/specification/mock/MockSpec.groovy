package specification.mock


import spock.lang.Specification

class MockSpec extends Specification {

    def "mock an interface"() {
        setup:
        def person = Mock(Person)
        person.name() >> "John"
        person.age() >> 30

        expect:
        person.name() == "John"
        person.age() == 30
    }

    def "mock an object"() {
        setup:
        def person = Mock(PersonMary)
        person.name() >> "John"
        person.age() >> 30

        expect:
        person.name() == "John"
        person.age() == 30
    }

}

