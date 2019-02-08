package scala.collection.map

import org.scalatest.{FlatSpec, Matchers}

class MapContains extends FlatSpec with Matchers {

  it should "check a map contains entry" in {
    val map = Map("x" -> 24, "y" -> 25)
    map.exists(_ == "y" -> 25) shouldBe true
  }

  it should "check a map contains key" in {
    val map = Map("x" -> 24, "y" -> 25)
    map.contains("y") shouldBe true
    map.exists(_._1 == "y") shouldBe true
    map.keySet.contains("y") shouldBe true
  }

  it should "check a map contains value" in {
    val map = Map("x" -> 24, "y" -> 25)
    map.values.exists(_ == 25) shouldBe true
    map.exists(_._2 == 25) shouldBe true
  }

}
