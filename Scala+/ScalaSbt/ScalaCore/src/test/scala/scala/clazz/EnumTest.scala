package scala.clazz

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.collection.mutable.ListBuffer

class EnumTest extends AnyFlatSpec with Matchers {

  it should "use enum" in {
    val toLeft = Direction.Left
    toLeft shouldBe Direction.Left
  }

  it should "get all enum values" in {
    val values = Direction.values
    values should contain allOf(Direction.Left, Direction.Forward, Direction.Right)
  }

  it should "use enum as method argument" in {
    val toLeft = Direction.Left
    getEnumName(toLeft) shouldBe "Left"
  }

  def getEnumName(enum: Direction.Category): String = enum.toString

  it should "iterate all enum values" in {
    val actual = ListBuffer[String]()
    Direction.values.foreach(actual += _.toString)
    actual should contain allOf("Left", "Forward", "Right")
  }

}

object Direction extends Enumeration {
  type Category = Value
  val Left, Forward, Right = Value
}
