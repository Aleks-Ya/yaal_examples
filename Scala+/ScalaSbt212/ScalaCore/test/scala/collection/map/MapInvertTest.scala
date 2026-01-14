package scala.collection.map

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class MapInvertTest extends AnyFlatSpec with Matchers {

  it should "swap map key and values" in {
    val map = Map("a" -> 1, "b" -> 2)
    val inverted = map.map(_.swap)
    inverted shouldEqual Map(1 -> "a", 2 -> "b")
  }

  it should "swap map key and values (with value duplicates)" in {
    val map = Map("a" -> 1, "b" -> 2, "c" -> 1)
    val inverted = map.map(_.swap)
    inverted shouldEqual Map(1 -> "c", 2 -> "b")
  }

}
