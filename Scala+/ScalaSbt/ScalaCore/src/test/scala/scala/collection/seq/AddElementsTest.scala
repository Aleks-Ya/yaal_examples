package scala.collection.seq

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class AddElementsTest extends AnyFlatSpec with Matchers {

  it should "join 2 immutable Seq" in {
    val s1 = Seq(1, 2)
    val s2 = Seq(3, 4)
    val joined = s1 ++ s2
    s1 should contain allOf(1, 2)
    s2 should contain allOf(3, 4)
    joined should contain inOrderOnly(1, 2, 3, 4)
  }

  it should "add element to end of an immutable Seq #1" in {
    val s = Seq(1, 2)
    val element = 3
    val joined = s :+ element
    s should contain allOf(1, 2)
    joined should contain inOrderOnly(1, 2, 3)
  }

  it should "add element to beginning of an immutable Seq" in {
    val s = Seq(1, 2)
    val element = 3
    val joined = element +: s
    s should contain allOf(1, 2)
    joined should contain inOrderOnly(3, 1, 2)
  }

  it should "add element to end of an immutable Seq #2" in {
    val s = Seq(1, 2)
    val element = 3
    val joined = s ++ Seq(element)
    s should contain allOf(1, 2)
    joined should contain inOrderOnly(1, 2, 3)
  }

}
