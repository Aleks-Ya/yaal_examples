package clazz.members.field

import org.junit.jupiter.api.Test

class FieldTest {
    @Test
    void main() {
        def customer = new Customer(id: 1, name: "Gromit", dob: new Date())
        println("Hello ${customer.name}")
        println("Now is ${customer.date}")
    }
}