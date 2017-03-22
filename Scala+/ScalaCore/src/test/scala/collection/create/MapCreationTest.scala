package collection.create

import org.scalatest.{FlatSpec, Matchers}

class MapCreationTest extends FlatSpec with Matchers {

  it should "create a map" in {
    val map1 = Map("x" -> 24, "y" -> 25)
    val map2 = Map(("x", 24), ("y", 25))
    println("Map1: " + map1)
    println("Map2: " + map2)
  }

}
