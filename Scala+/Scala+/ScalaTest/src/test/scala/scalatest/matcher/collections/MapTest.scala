package scalatest.matcher.collections

import java.util

import org.scalatest.Entry
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class MapTest extends AnyFlatSpec with Matchers {

  it should "assert Scala Map" in {
    val scalaMap = Map("x" -> 24, "y" -> 25)
    scalaMap should have size 2
    scalaMap should contain key "x"
    scalaMap should contain value 24
    scalaMap should contain("x" -> 24)
    scalaMap should contain allOf("x" -> 24, "y" -> 25)
    scalaMap should contain only("x" -> 24, "y" -> 25)
  }

  it should "assert java.util.Map" in {
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

}