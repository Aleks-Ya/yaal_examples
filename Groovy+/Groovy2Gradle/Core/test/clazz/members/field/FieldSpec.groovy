package clazz.members.field


import spock.lang.Specification

class FieldSpec extends Specification {

    def "test customer greeting and current date"() {
        given:
        def customer = new Customer(id: 1, name: "Gromit", dob: new Date())

        expect:
        println("Hello ${customer.name}")
        println("Now is ${customer.date}")
    }
}
