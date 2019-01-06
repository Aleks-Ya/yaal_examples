package scalatest.matcher

import org.scalatest.{FlatSpec, Matchers}

class NumbersTest extends FlatSpec with Matchers {

  "shouldEqual matcher" should "work" in {
    val result = 3
    result should equal(3) // can customize equality
    result should ===(3) // can customize equality and enforce type constraints
    result should be(3) // cannot customize equality, so fastest to compile
    result shouldEqual 3 // can customize equality, no parentheses required
    result shouldBe 3 // cannot customize equality, so fastest to compile, no parentheses required
  }

  "NaN matcher" should "work" in {
    val d = Double.NaN
    d.isNaN shouldBe true
  }

}