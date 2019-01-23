package scalatest.matcher

import org.scalatest.{FlatSpec, Matchers}

class CombiningMatchersTest extends FlatSpec with Matchers {

  /**
    * Docs: http://www.scalatest.org/user_guide/using_matchers#logicalExpressions
    */
  it should "use logical expressions" in {
    val result = 3
    result should (be(equal(3)) or be(3))

    val map = Map("two" -> 24, "ouch" -> 25)
    map should (contain key "two" and not contain value(7))
    map should (not be null and contain key "ouch")

    val number = 1
    number should (be > 0 and be <= 10)

    val option = Option(List(1, 2, 3))
    option should (equal(Some(List(1, 2, 3))) or be(None))

    "yellow" should (equal("blue") or equal {
      println("hello, world!")
      "yellow"
    })

    val traversable = List(1, 2, 3)
    traversable should (contain(7) or contain(2) and have size 3)
    traversable should ((contain(7) or contain(2)) and have size 3)
    traversable should (contain(7) or (contain(2) and have size 3))

  }

}