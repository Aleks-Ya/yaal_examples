package dataframe.function.builtin

import factory.Factory
import org.apache.spark.sql.functions.{col, lit, struct, when}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class StructFunction extends AnyFlatSpec with Matchers {
  it should "use struct function" in {
    val df = Factory.peopleDf
    val updatedDf = df.select(
      col("name"),
      struct(col("name"), col("age"), col("gender")) as "details",
      struct(col("name"), struct(col("age"), when(col("age") > 20, "adult").otherwise("child") as "category") as "age_details") as "age_info"
    )
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"name":"John","details":{"name":"John","age":25,"gender":"M"},"age_info":{"name":"John","age_details":{"age":25,"category":"adult"}}}""",
      """{"name":"Peter","details":{"name":"Peter","age":35,"gender":"M"},"age_info":{"name":"Peter","age_details":{"age":35,"category":"adult"}}}""",
      """{"name":"Mary","details":{"name":"Mary","age":20,"gender":"F"},"age_info":{"name":"Mary","age_details":{"age":20,"category":"child"}}}"""
    )
  }

  it should "use struct of lit(null)" in {
    val df = Factory.peopleDf
    val updatedDf = df.select(
      col("name"),
      struct(col("age"), lit(null) as "empty", when(col("age") > 20, "adult") as "adultery") as "details"
    )
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"name":"John","details":{"age":25,"empty":null,"adultery":"adult"}}""",
      """{"name":"Peter","details":{"age":35,"empty":null,"adultery":"adult"}}""",
      """{"name":"Mary","details":{"age":20,"empty":null,"adultery":null}}"""
    )
  }
}