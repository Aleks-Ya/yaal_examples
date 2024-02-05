package scala.collection.list

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ListToMapTest extends AnyFlatSpec with Matchers {

  it should "convert list to map" in {
    val tuples = Seq(("a", 1), ("b", 2), ("c", 3))
    val map = tuples.toMap
    map should contain allOf("a" -> 1, "b" -> 2, "c" -> 3)
  }

}
