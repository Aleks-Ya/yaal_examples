package scala.collection.map

import org.scalatest.{FlatSpec, Matchers}

class MapMapTest extends FlatSpec with Matchers {

  it should "modify each value in a Map" in {
    val map = Map("x" -> 1, "y" -> 2)
    val newMap = map.mapValues(value => value * 2)
    newMap should contain allOf("x" -> 2, "y" -> 4)
  }

}
