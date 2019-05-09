package clazz

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class PojoTest : StringSpec({
    "instantiate Person class" {
        val name = "John"
        val age = 30
        val p = Person(name, age)
        p.name.contentEquals(name)
        p.age.shouldBe(age)
    }
})