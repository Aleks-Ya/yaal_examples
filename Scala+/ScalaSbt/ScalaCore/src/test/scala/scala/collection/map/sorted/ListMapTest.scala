package scala.collection.map.sorted

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.collection.immutable.ListMap

/**
 * [[ListMap]] preserves the insertion order.
 * Can sort by keys or values.
 */
class ListMapTest extends AnyFlatSpec with Matchers {

  it should "sort map by key in ascending order" in {
    val map = Map(3 -> "a", 1 -> "b", 2 -> "c")
    val sorted = ListMap(map.toSeq.sortBy(_._1): _*)
    sorted.toString shouldEqual "ListMap(1 -> b, 2 -> c, 3 -> a)"
  }

  it should "sort map by value in ascending order" in {
    val map = Map("x" -> 3, "y" -> 1, "z" -> 2)
    val sorted = ListMap(map.toSeq.sortBy(_._2): _*)
    sorted.toString shouldEqual "ListMap(y -> 1, z -> 2, x -> 3)"
  }

  it should "sort map by value in descending order" in {
    val map = Map("x" -> 3, "y" -> 1, "z" -> 2)
    val sorted = ListMap(map.toSeq.sortWith((a, b) => a._2 > b._2): _*)
    sorted.toString shouldEqual "ListMap(x -> 3, z -> 2, y -> 1)"
  }
}
