package scala.collection.map.sorted

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.collection.immutable.TreeMap

/**
 * [[TreeMap]] preserves the key natural order.
 * Cannot sort by values.
 */
class TreeMapTest extends AnyFlatSpec with Matchers {

  it should "sort map by key in ascending order" in {
    val sorted = TreeMap(3 -> "a", 1 -> "b", 2 -> "c")
    sorted.toString shouldEqual "TreeMap(1 -> b, 2 -> c, 3 -> a)"
  }

  it should "sort map by key in descending order" in {
    val sorted = TreeMap(3 -> "a", 1 -> "b", 2 -> "c")(Ordering[Int].reverse)
    sorted.toString shouldEqual "TreeMap(3 -> a, 2 -> c, 1 -> b)"
  }

  it should "convert a Map to a TreeMap" in {
    val map = Map(3 -> "a", 1 -> "b", 2 -> "c")
    val sorted = TreeMap(map.toSeq: _*)
    sorted.toString shouldEqual "TreeMap(1 -> b, 2 -> c, 3 -> a)"
  }

}
