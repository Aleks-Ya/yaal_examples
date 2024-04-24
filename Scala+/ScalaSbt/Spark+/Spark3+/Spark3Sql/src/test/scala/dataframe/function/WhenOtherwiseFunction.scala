package dataframe.function

import factory.Factory
import org.apache.spark.sql.functions.{col, when}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class WhenOtherwiseFunction extends AnyFlatSpec with Matchers {
  it should "use when-otherwise function" in {
    val df = Factory.peopleDf
    val updatedDf = df.select(col("name"), when(col("gender") === "M", "Man").otherwise("Woman") as "full_gender")
    updatedDf.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","full_gender":"Man"}""",
      """{"name":"Peter","full_gender":"Man"}""",
      """{"name":"Mary","full_gender":"Woman"}"""
    )
  }
}