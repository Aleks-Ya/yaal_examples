package kotlintest.style

import io.kotlintest.shouldBe
import io.kotlintest.specs.FeatureSpec
import io.kotlintest.specs.FunSpec

class FeatureSpecTest : FeatureSpec({
    feature("the can of coke") {
        scenario("should be fizzy when I shake it") {
            // test here
        }
        scenario("and should be tasty") {
            // test heree
        }
    }
})