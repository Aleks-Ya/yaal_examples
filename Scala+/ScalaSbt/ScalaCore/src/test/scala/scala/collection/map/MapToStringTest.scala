package scala.collection.map

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class MapToStringTest extends AnyFlatSpec with Matchers {

  it should "map to string" in {
    val map = Map("x" -> 24, "y" -> 25)
    val str = map.toString
    str shouldEqual "Map(x -> 24, y -> 25)"
  }

  it should "mkString1" in {
    val map = Map("x" -> 24, "y" -> 25)
    val str = map.mkString("[", ", ", "]")
    str shouldEqual "[x -> 24, y -> 25]"
  }

  it should "mkString" in {
    val map = Map("x" -> 24, "y" -> 25)
    val str = map.map { case (k, v) => s"$k=$v" }.mkString("[", ", ", "]")
    str shouldEqual "[x=24, y=25]"
  }

}
