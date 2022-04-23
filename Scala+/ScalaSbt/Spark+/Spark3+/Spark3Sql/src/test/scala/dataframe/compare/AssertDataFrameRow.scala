package dataframe.compare

import factory.Factory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * Assert DataFrame Row in unit tests.
 */
class AssertDataFrameRow extends AnyFlatSpec with Matchers {

  it should "assert a Row content" in {
    val row = Factory.peopleDf.first()
    row.toString() shouldBe "[John,25,M]"
    row.toSeq shouldBe Seq("John", 25, "M")
    row.json shouldBe """{"name":"John","age":25,"gender":"M"}"""
    row.mkString shouldBe "John25M"
    row.mkString(",") shouldBe "John,25,M"
  }

  it should "assert an Array[Row] content" in {
    val rows2 = Factory.peopleDf.head(2)
    rows2.map(_.toSeq) should contain inOrderOnly(
      Seq("John", 25, "M"),
      Seq("Peter", 35, "M")
    )
    rows2.map(_.json) should contain inOrderOnly(
      """{"name":"John","age":25,"gender":"M"}""",
      """{"name":"Peter","age":35,"gender":"M"}"""
    )
    rows2.mkString shouldBe "[John,25,M][Peter,35,M]"
    rows2.mkString(",") shouldBe "[John,25,M],[Peter,35,M]"
  }

}