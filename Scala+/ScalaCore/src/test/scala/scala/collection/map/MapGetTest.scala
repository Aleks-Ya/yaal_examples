package scala.collection.map

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class MapGetTest extends AnyFlatSpec with Matchers {

  it should "create a map" in {
    val map1 = Map("x" -> 24, "y" -> 25)
    map1("x") shouldEqual 24
  }

  it should "not found not exist element" in {
    assertThrows[NoSuchElementException] {
      val map = Map("x" -> 24)
      map("z")
    }
  }

}
