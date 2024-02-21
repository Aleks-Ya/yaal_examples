package scalatest.matcher

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ClassTest extends AnyFlatSpec with Matchers {

  it should "assert class of a primitive" in {
    val s = "a"
    s shouldBe a[String]
    s should not be an[Int]
  }

  it should "assert class of a collection" in {
    Seq(1, 2) shouldBe a[Seq[_]]
    Array(1, 2) shouldBe a[Array[_]]
  }
}