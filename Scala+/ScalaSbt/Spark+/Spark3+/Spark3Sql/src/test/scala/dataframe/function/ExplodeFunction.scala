package dataframe.function

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{col, explode}
import org.apache.spark.sql.types.{ArrayType, StringType}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class ExplodeFunction extends AnyFlatSpec with Matchers {
  it should "use explode function" in {
    val df = Factory.createDf(Map("name" -> StringType, "cities" -> ArrayType(StringType)),
      Row("John", List("London", "Paris")),
      Row("Mary", List("Berlin", "Paris")))
    val explodedDf = df.select(col("name"), explode(col("cities")).alias("city"))
    explodedDf.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","city":"London"}""",
      """{"name":"John","city":"Paris"}""",
      """{"name":"Mary","city":"Berlin"}""",
      """{"name":"Mary","city":"Paris"}"""
    )
  }
}