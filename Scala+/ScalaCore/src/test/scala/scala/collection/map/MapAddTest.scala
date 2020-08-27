package scala.collection.map

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class MapAddTest extends AnyFlatSpec with Matchers {

  it should "add entry to mutable map" in {
    val map = scala.collection.mutable.Map[String, Int]()

    map += "a" -> 1
    map += "b" -> 2

    map should contain key "a"
    map should contain key "b"
    map should contain value 1
    map should contain value 2
  }

  it should "add entry to immutable map" in {
    val map = Map("a" -> 1)
    val newMap = map + ("b" -> 2)
    newMap should contain only("a" -> 1, "b" -> 2)
  }

}
