package scalatest.matcher

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class OptionTest extends AnyFlatSpec with Matchers {

  "None" should "pass" in {
    val option = None
    option shouldEqual None
    option shouldBe None
    option should ===(None)
  }

  "Some" should "pass" in {
    val option = Some("hi")
    option shouldEqual Some("hi")
    option shouldBe Some("hi")
    option should ===(Some("hi"))
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