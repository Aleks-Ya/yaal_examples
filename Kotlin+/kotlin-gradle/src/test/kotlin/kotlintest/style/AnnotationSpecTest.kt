package kotlintest.style

import io.kotlintest.shouldBe
import io.kotlintest.specs.AnnotationSpec

class AnnotationSpecTest : AnnotationSpec() {

    @BeforeEach
    fun beforeTest() {
        println("Before each test")
    }

    @Test
    fun test1() {
        1 shouldBe 1
    }

    @Test
    fun test2() {
        3 shouldBe 3
    }
}