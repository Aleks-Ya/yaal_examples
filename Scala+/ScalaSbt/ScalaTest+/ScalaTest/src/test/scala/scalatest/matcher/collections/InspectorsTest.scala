package scalatest.matcher.collections

import org.scalatest.Inspectors
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class InspectorsTest extends AnyFlatSpec with Matchers with Inspectors {

  "forAll inspector" should "assert all elements of a list" in {
    val xs = List(1, 2, 3)
    forAll(xs) { x => x should be < 10 }
    all(xs) should be < 10
  }

  "forAll inspector" should "assert list of lists" in {
    val yss = List(
      List(1, 2, 3),
      List(1, 2, 3),
      List(1, 2, 3)
    )
    forAll(yss) { ys =>
      forAll(ys) { y => y should be > 0 }
    }
  }

  it should "use atLeast inspector" in {
    val xs = List(1, 2, 3)
    atLeast(2, xs) should be > 1
  }

  it should "use atMost inspector" in {
    val xs = List(1, 2, 3)
    atMost(1, xs) should be > 2
  }

  it should "use between inspector" in {
    val xs = List(1, 2, 3)
    between(1, 2, xs) should be > 2
  }

  it should "use exactly inspector" in {
    val xs = List(1, 2, 3)
    exactly(2, xs) should be > 1
  }

  it should "use every inspector" in {
    val xs = List(1, 2, 3)
    every(xs) should be > 0
  }

}