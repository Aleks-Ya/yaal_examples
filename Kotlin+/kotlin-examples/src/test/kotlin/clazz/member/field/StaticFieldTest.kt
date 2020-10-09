package clazz.member.field

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec


class StaticFieldTest : StringSpec({
    "read a static field" {
        StaticField.MY_STATIC_FILED.shouldBe(20)
    }
})