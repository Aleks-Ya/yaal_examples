package scala.collection.map

import org.scalatest.{FlatSpec, Matchers}

class MapCreateTest extends FlatSpec with Matchers {

  it should "create a map" in {
    val map1 = Map("x" -> 24, "y" -> 25)
    val map2 = Map(("x", 24), ("y", 25))
    println("Map1: " + map1)
    println("Map2: " + map2)
  }

  it should "create an empty map" in {
    val map = scala.collection.mutable.Map[String, Int]()
    map shouldBe empty
    map += "a" -> 1
    map should not be empty
  }

}
