package scalatest.matcher

import org.scalatest.{FlatSpec, Matchers}

class OptionTest extends FlatSpec with Matchers {

  "None" should "pass" in {
    val option = None
    option shouldEqual None
    option shouldBe None
    option should ===(None)
    option shouldBe empty
  }

  "Some" should "pass" in {
    val option = Some("hi")
    option shouldEqual Some("hi")
    option shouldBe Some("hi")
    option should ===(Some("hi"))
    option shouldBe defined
  }

  "Contain" should "pass" in {
    Some(2) should contain(2)
    Some(7) should contain oneOf(5, 7, 9)
    Some(0) should contain noneOf(7, 8, 9)
  }

  "Additional check" should "pass" in {
    val option = Some(1)
    option.value should be < 7
  }

}