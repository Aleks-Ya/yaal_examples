package scala.clazz.traits

import org.scalatest.Inspectors
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class MultiTraitsTest extends AnyFlatSpec with Matchers with Inspectors {

  it should "use two traits" in {
    val xs = List(1, 2, 3)
    forAll(xs) { x => x should be < 10 }
  }

}
