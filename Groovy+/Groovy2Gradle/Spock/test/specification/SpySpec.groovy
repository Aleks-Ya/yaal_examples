package specification


import spock.lang.Specification

class SpySpec extends Specification {

    def "partial mock"() {
        setup:
        def person = new PersonMary()
        def spy = Spy(person)
        spy.age() >> 20

        expect:
        spy.name() == "Mary"
        spy.age() == 20
    }
}

