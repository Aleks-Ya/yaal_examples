package kotlintest.style

import io.kotlintest.specs.ExpectSpec

class ExpectSpecTest : ExpectSpec({
    context("a calculator") {
        expect("simple addition") {
            // test here
        }
        expect("integer overflow") {
            // test here
        }
    }
})