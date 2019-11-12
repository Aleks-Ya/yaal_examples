package clazz

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec


class DataTest : StringSpec({
    "data class overrides equals() method" {
        val name = "John"
        val age = 30
        val p1 = Person2(name, age)
        val p2 = Person2(name, age)
        p1.shouldBe(p2)
    }
})