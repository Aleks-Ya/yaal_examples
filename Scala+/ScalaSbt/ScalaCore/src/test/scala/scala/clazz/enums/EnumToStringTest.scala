package scala.clazz.enums

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class EnumToStringTest extends AnyFlatSpec with Matchers {

  it should "default to string" in {
    DirectionDefaultToString.Left.toString shouldBe "Left"
    DirectionDefaultToString.Right.toString shouldBe "Right"
  }

  it should "custom to string" in {
    DirectionCustomToString.Left.toString shouldBe "Turn left"
    DirectionCustomToString.Right.toString shouldBe "Turn right"
  }

}

object DirectionDefaultToString extends Enumeration {
  type Category = Value
  val Left, Right = Value
}

object DirectionCustomToString extends Enumeration {
  type DirectionCustomToString = Val
  val Left: DirectionCustomToString = new Val {
    override def toString = "Turn left"
  }
  val Right: DirectionCustomToString = new Val {
    override def toString = "Turn right"
  }
}
