package scala.collection.map

import org.scalatest.{FlatSpec, Matchers}

class MapAddTest extends FlatSpec with Matchers {

  it should "add single entry to map" in {
    val map = scala.collection.mutable.Map[String, Int]()

    map += "a" -> 1
    map += "b" -> 2

    map should contain key "a"
    map should contain key "b"
    map should contain value 1
    map should contain value 2
  }

}
