package collection.map

import org.scalatest.{FlatSpec, Matchers}

class MapReduceTest extends FlatSpec with Matchers {

  it should "reduce map by key" in {
    val map = Map("x" -> 1, "y" -> 2)
    val reduced = map.reduce((entry1, entry2) => (entry1._1 + entry2._1, entry1._2 + entry2._2))
    reduced._1 shouldEqual "xy"
    reduced._2 shouldEqual 3
  }

  it should "join two maps" in {
    val map1 = Map("x" -> 1, "y" -> 2)
    val map2 = Map("x" -> 10, "z" -> 3)
    val joined = map1 ++ map2.map { case (k, v) => k -> (v + map1.getOrElse(k, 0)) }
    joined should have size 3
    joined("x") shouldEqual 11
    joined("y") shouldEqual 2
    joined("z") shouldEqual 3
  }
}
