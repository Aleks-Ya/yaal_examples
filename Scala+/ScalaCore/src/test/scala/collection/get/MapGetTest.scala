package collection.get

import org.scalatest.{FlatSpec, Matchers}

class MapGetTest extends FlatSpec with Matchers {

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
