package scala.collection.seq

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class AddElementsTest extends AnyFlatSpec with Matchers {

  it should "join 2 immutable Seq" in {
    val s1 = Seq(1, 2)
    val s2 = Seq(3, 4)
    val joined = s1 ++ s2
    joined should contain allOf(1, 2, 3, 4)
  }

  it should "add element to an immutable Seq #1" in {
    val s = Seq(1, 2)
    val element = 3
    val joined = s :+ element
    joined should contain allOf(1, 2, 3)
  }

  it should "add element to an immutable Seq #2" in {
    val s = Seq(1, 2)
    val element = 3
    val joined = s ++ Seq(element)
    joined should contain allOf(1, 2, 3)
  }

}
