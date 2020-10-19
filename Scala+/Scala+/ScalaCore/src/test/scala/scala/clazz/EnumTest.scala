package scala.clazz

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class EnumTest extends AnyFlatSpec with Matchers {

  it should "use enum" in {
    val toLeft = Direction.Left
    val values = Direction.values
    values should contain allOf(Direction.Left, Direction.Forward, Direction.Right)
  }

}

object Direction extends Enumeration {
  type Category = Value
  val Left, Forward, Right = Value
}
