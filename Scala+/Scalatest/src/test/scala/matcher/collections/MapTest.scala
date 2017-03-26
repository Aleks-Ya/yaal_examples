package matcher.collections

import org.scalatest.{FlatSpec, Matchers}

class MapTest extends FlatSpec with Matchers {

  "map" should "work" in {
    val map = Map("x" -> 24, "y" -> 25)
    map should contain key "x"
    map should contain value 24
    //    map should contain  (Entry("x", 24))
    //    map should contain only (Entry("x", 24))
  }

  it should "verify empty maps" in {
    Map() shouldBe empty
    Map(1 -> 2) should not be empty
  }


}