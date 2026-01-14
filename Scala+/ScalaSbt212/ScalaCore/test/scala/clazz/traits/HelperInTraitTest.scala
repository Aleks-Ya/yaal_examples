package scala.clazz.traits

import org.scalatest.Inspectors
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class HelperInTraitTest extends AnyFlatSpec with Matchers with Inspectors {

  it should "use helper methods from traits" in {
    val calculator = new Calculator()
    calculator.calculate(2, 3, 4) shouldBe "Number 10"
  }

}
