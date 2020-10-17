package clazz.generic

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

data class Pair<K, V>(val key: K, val value: V)

class GenericClassTest : StringSpec({
    "generic data class" {
        val key = "John"
        val value = 30
        val pair = Pair(key, value)
        pair.key shouldBe key
        pair.value shouldBe value
    }
})