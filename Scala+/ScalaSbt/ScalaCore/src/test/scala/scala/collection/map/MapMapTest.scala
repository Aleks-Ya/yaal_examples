package scala.collection.map

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class MapMapTest extends AnyFlatSpec with Matchers {

  it should "modify each value in a Map" in {
    val map = Map("x" -> 1, "y" -> 2)
    val newMap = map.mapValues(value => value * 2)
    newMap should contain allOf("x" -> 2, "y" -> 4)
  }

}
