package kotlintest.matcher

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class NumberMatcherTest : StringSpec({
    "Int matchers" {
        1.shouldBe(1)
    }
})