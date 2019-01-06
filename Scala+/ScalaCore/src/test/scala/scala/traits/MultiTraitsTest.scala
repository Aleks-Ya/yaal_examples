package scala.traits

import org.scalatest.{FlatSpec, Inspectors, Matchers}

class MultiTraitsTest extends FlatSpec with Matchers with Inspectors {

  it should "use two traits" in {
    val xs = List(1, 2, 3)
    forAll(xs) { x => x should be < 10 }
  }

}
