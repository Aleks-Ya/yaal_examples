package matcher.collections

import org.scalatest.{FlatSpec, Matchers}

class SequencesTest extends FlatSpec with Matchers {

  "sequences with integers" should "work" in {
    List(1, 2, 2, 3, 3, 3) should contain inOrderOnly(1, 2, 3)
    List(1, 2, 2, 3, 3, 3) should contain(1)
  }

  "sequences with strings" should "work" in {
    List("a", "b", "c") should contain inOrderOnly("a", "b", "c")
    List("a", "b", "c") should contain("b")
  }

}