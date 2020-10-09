package kotlintest.style

import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

class WordSpecTest : WordSpec({
    "String.length" should {
        "return the length of the string" {
            "sammy".length shouldBe 5
            "".length shouldBe 0
        }
    }
})