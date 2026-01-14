package scalatest.matcher.collections

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ArrayTest extends AnyFlatSpec with Matchers {

  "array" should "work" in {
    val arr = Array(1, 2, 3)
    val arr2 = Array()

    arr should have length 3
    arr should have size 3
    arr should equal(Array(1, 2, 3))
    arr should not be empty
    arr2 shouldBe empty
    arr should contain(2)
    arr should not contain 5
    arr should contain oneOf(1, 4)
    arr should contain allOf(1, 2)
    arr should contain inOrder(1, 3)
    arr should contain inOrderOnly(1, 2, 3)
    arr should contain atLeastOneOf(1, 4)
    arr should contain atMostOneOf(1, 4)
    arr should contain allElementsOf Seq(1, 3)
    arr should contain inOrderElementsOf Seq(1, 3)
  }

  "array sort" should "work" in {
    val arr1 = Array(1, 2, 3)
    val arr2 = Array(2, 1, 3)
    arr1 shouldBe sorted
    arr2 should not be sorted
  }

  "array elements" should "work" in {
    val arr = Array(1, 2, 3)

    //    NOT COMPILING
    //    all(arr) should be > 0
    //    atMost(2, arr) should be > 1
    //    atLeast(1, arr) should be < 2
    //    between(2, 3, arr) should (be > 1 and be < 5)
    //    exactly (2, arr) should be <= 2
    //    every (arr) should be < 10
  }
}