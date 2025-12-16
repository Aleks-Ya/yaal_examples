package specification.mock


import spock.lang.Specification

class ExpandoSpec extends Specification {

    def "add a method to an object"() {
        setup:
        Expando person = new Expando()
        person.name = { "John" }
        person.age = { 30 }

        expect:
        person.name() == "John"
        person.age() == 30
    }

}

