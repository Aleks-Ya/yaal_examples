package clazz.interfaces

import org.junit.jupiter.api.Test

class ImplementInterfaceTest {
    @Test
    fun `a common class inherits an interface`() {
        val p = Person()
        p.getName().contentEquals("John")
    }

    @Test
    fun `a data class inherits an interface`() {
        val n = "John"
        val p = PersonData(n)
        p.getName().contentEquals(n)
    }
}

interface Named {
    fun getName(): String
}

class Person : Named {
    override fun getName(): String = "John"
}

data class PersonData(val personName: String) : Named {
    override fun getName(): String {
        return personName
    }
}