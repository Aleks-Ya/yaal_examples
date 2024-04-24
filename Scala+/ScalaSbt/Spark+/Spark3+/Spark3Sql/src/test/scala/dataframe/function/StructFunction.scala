package dataframe.function

import factory.Factory
import org.apache.spark.sql.functions.{col, struct, when}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class StructFunction extends AnyFlatSpec with Matchers {
  it should "use struct function" in {
    val df = Factory.peopleDf
    val updatedDf = df.select(
      col("name"),
      struct(col("name"), col("age"), col("gender")) as "full_info",
      struct(col("name"), struct(col("age"), when(col("age") > 20, "adult").otherwise("child") as "category") as "age_details") as "age_info",
    )
    updatedDf.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","full_info":{"name":"John","age":25,"gender":"M"},"age_info":{"name":"John","age_details":{"age":25,"category":"adult"}}}""",
      """{"name":"Peter","full_info":{"name":"Peter","age":35,"gender":"M"},"age_info":{"name":"Peter","age_details":{"age":35,"category":"adult"}}}""",
      """{"name":"Mary","full_info":{"name":"Mary","age":20,"gender":"F"},"age_info":{"name":"Mary","age_details":{"age":20,"category":"child"}}}"""
    )
  }
}