package kotlintest.style

import io.kotlintest.specs.DescribeSpec

class DescribeSpecTest : DescribeSpec({
    describe("score") {
        it("start as zero") {
            // test here
        }
        context("with a strike") {
            it("adds ten") {
                // test here
            }
            it("carries strike to the next frame") {
                // test here
            }
        }
    }
})