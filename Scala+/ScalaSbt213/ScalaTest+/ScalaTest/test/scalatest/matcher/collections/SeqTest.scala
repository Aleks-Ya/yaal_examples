package scalatest.matcher.collections

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class SeqTest extends AnyFlatSpec with Matchers {

  "sequences with integers" should "work" in {
    val list = List(1, 2, 2, 3, 3, 3)
    list should have size 6
    list.size should be > 5
    list.size should be >= 5
    list should contain only(1, 2, 3)
    list should contain inOrderOnly(1, 2, 3)
    list should contain(1)
    list should contain allOf(1, 2)
    list should contain allElementsOf Seq(3, 2, 1)
    list should contain theSameElementsInOrderAs Seq(1, 2, 2, 3, 3, 3)
    list should contain theSameElementsAs Seq(3, 2, 1, 2, 3, 3)
    list shouldEqual Seq(1, 2, 2, 3, 3, 3)
    all(list) should be > 0
  }

  "sequences with strings" should "work" in {
    val list = List("a", "b", "c", "c")
    list should have size 4
    list.size should be > 3
    list.size should be >= 3
    list should contain only("a", "b", "c")
    list should contain inOrderOnly("a", "b", "c")
    list should contain("b")
    list should contain allOf("a", "b")
    list should contain allElementsOf Seq("c", "b", "a")
    list should contain theSameElementsInOrderAs Seq("a", "b", "c", "c")
    list should contain theSameElementsAs Seq("c", "b", "a", "c")
    list shouldEqual Seq("a", "b", "c", "c")
    all(list) should not be null
  }

}