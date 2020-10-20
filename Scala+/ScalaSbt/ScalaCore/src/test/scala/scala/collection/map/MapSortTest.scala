package scala.collection.map

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.collection.immutable.ListMap

class MapSortTest extends AnyFlatSpec with Matchers {

  it should "sort map by value in ascending order" in {
    val map = Map("x" -> 3, "y" -> 1, "z" -> 2)
    val sorted = ListMap(map.toSeq.sortBy(_._2): _*)
    sorted.toString shouldEqual "ListMap(y -> 1, z -> 2, x -> 3)"
  }

  it should "sort map by value in descending order" in {
    val map = Map("x" -> 3, "y" -> 1, "z" -> 2)
    val sorted = ListMap(map.toSeq.sortWith(_._2 > _._2):_*)
    sorted.toString shouldEqual "ListMap(x -> 3, z -> 2, y -> 1)"
  }
}
