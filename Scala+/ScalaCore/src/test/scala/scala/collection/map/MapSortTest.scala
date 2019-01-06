package scala.collection.map

import org.scalatest.{FlatSpec, Matchers}

import scala.collection.immutable.ListMap

class MapSortTest extends FlatSpec with Matchers {

  it should "sort map by value in ascending order" in {
    val map = Map("x" -> 3, "y" -> 1, "z" -> 2)
    val sorted = ListMap(map.toSeq.sortBy(_._2): _*)
    sorted.toString shouldEqual "Map(y -> 1, z -> 2, x -> 3)"
  }

  it should "sort map by value in descending order" in {
    val map = Map("x" -> 3, "y" -> 1, "z" -> 2)
    val sorted = ListMap(map.toSeq.sortWith(_._2 > _._2):_*)
    sorted.toString shouldEqual "Map(x -> 3, z -> 2, y -> 1)"
  }
}
