package scala.collection.map

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class MapMapTest extends AnyFlatSpec with Matchers {

  it should "modify each value in a Map" in {
    val map = Map("x" -> 1, "y" -> 2)
    val newMap = map.view.mapValues(value => value * 2)
    newMap.toMap should contain allOf("x" -> 2, "y" -> 4)
  }

  it should "convert a Map to a List" in {
    val map = Map("x" -> 1, "y" -> 2)
    val list = map.map { case (k, v) => s"$k-$v" }.toList
    list should contain inOrderOnly("x-1", "y-2")
  }

}
