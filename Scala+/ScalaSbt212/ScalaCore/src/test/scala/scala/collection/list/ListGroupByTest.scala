package scala.collection.list

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ListGroupByTest extends AnyFlatSpec with Matchers {

  it should "group list elements by identity" in {
    val list = List("a", "b", "a", "c", "b", "a")
    val map = list.groupBy(identity)
    map should contain allOf(("a", List("a", "a", "a")), ("b", List("b", "b")), ("c", List("c")))
  }

  it should "group list elements by expression" in {
    val list = List("aaa", "bbb", "cc", "dd", "e")
    val map = list.groupBy(_.length)
    map should contain allOf((3, List("aaa", "bbb")), (2, List("cc", "dd")), (1, List("e")))
  }

}
