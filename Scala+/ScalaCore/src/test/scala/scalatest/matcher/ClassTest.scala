package scalatest.matcher

import org.scalatest.{FlatSpec, Matchers}

class ClassTest extends FlatSpec with Matchers {

  it should "assert class of a primitive" in {
    val s = "a"
    s shouldBe a[String]
    s should not be an[Int]
  }

  it should "assert class of a collection" in {
    Seq(1, 2) shouldBe a[Seq[_]]
    Array(1, 2) shouldBe a[Array[Int]]
  }
}