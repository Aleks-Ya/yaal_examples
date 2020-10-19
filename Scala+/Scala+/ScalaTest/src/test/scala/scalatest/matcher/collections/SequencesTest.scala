package scalatest.matcher.collections

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class SequencesTest extends AnyFlatSpec with Matchers {

  "sequences with integers" should "work" in {
    val list = List(1, 2, 2, 3, 3, 3)
    list should have size (6)
    list should contain inOrderOnly(1, 2, 3)
    list should contain(1)
    list should contain allOf(1, 2)
  }

  "sequences with strings" should "work" in {
    val list = List("a", "b", "c")
    list should have size (3)
    list should contain inOrderOnly("a", "b", "c")
    list should contain("b")
    list should contain allOf("a", "b")
  }

}