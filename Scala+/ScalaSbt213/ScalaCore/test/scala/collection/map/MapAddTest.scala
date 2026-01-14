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

  it should "add a map to a mutable map" in {
    val map1 = scala.collection.mutable.Map[String, Int]("b" -> 2)
    val map2 = Map("a" -> 1)
    map1 ++= map2
    map1 should contain allOf("a" -> 1, "b" -> 2)
  }

  it should "add entry to immutable map" in {
    val map = Map("a" -> 1)
    val newMap = map + ("b" -> 2)
    newMap should contain only("a" -> 1, "b" -> 2)
  }

  it should "join 2 immutable maps" in {
    val map1 = Map("a" -> 1, "c" -> 3)
    val map2 = Map("b" -> 2, "c" -> 4)
    val newMap = map1 ++ map2
    newMap should contain only("a" -> 1, "b" -> 2, "c" -> 4)
  }

  it should "overwrite entry by joining immutable maps" in {
    val map1 = Map("a" -> 1)
    val map2 = Map("a" -> 2, "b" -> 3)
    val newMap = map1 ++ map2
    newMap should contain only("a" -> 2, "b" -> 3)
  }

}
