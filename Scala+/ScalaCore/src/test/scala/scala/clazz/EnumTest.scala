package scala.clazz

import org.scalatest.{FlatSpec, Matchers}

class EnumTest extends FlatSpec with Matchers {

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
