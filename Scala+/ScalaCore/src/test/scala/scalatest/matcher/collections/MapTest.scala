package scalatest.matcher.collections

import java.util

import org.scalatest.{Entry, FlatSpec, Matchers}

class MapTest extends FlatSpec with Matchers {

  "scala map" should "work" in {
    val scalaMap = Map("x" -> 24, "y" -> 25)
    scalaMap should have size 2
    scalaMap should contain key "x"
    scalaMap should contain value 24
  }

  "java map" should "work" in {
    val javaMap = new util.HashMap[String, Integer]()
    javaMap.put("x", 24)
    javaMap.put("y", 25)

    javaMap should have size 2
    javaMap should contain key "x"
    javaMap should contain value 24
    javaMap should contain(Entry("x", 24))
    javaMap should contain allOf(Entry("x", 24), Entry("y", 25))
    javaMap should contain oneOf(Entry("a", 1), Entry("x", 24))
  }

  it should "verify empty maps" in {
    Map() shouldBe empty
    Map(1 -> 2) should not be empty
  }


}