package kotlintest.matcher

import io.kotlintest.matchers.string.shouldBeLowerCase
import io.kotlintest.matchers.string.shouldContain
import io.kotlintest.specs.StringSpec

class StringMatcherTest : StringSpec({
    "string matchers" {
        "substring".shouldContain("str")
        "abc".shouldBeLowerCase()
    }
})