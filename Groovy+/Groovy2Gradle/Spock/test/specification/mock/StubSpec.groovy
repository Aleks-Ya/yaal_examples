package specification.mock


import spock.lang.Specification

class StubSpec extends Specification {

    def "stub an interface"() {
        setup:
        def person = Stub(Person)
        person.name() >> "John"
        person.age() >> 30

        expect:
        person.name() == "John"
        person.age() == 30
    }
}

