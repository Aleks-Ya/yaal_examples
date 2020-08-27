package scalatest.collections

import org.scalatest.Inspectors
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class SeqInspectorTest extends AnyFlatSpec with Matchers with Inspectors {

  it should "assert all elements of a list" in {
    val xs = List(1, 2, 3)
    forAll(xs) { x => x should be < 10 }
  }

  it should "assert list of lists" in {
    val yss =
      List(
        List(1, 2, 3),
        List(1, 2, 3),
        List(1, 2, 3)
      )

    forAll(yss) { ys =>
      forAll(ys) { y => y should be > 0 }
    }
  }

}