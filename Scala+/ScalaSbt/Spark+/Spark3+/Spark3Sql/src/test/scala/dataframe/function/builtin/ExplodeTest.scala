package dataframe.function.builtin

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{col, explode}
import org.apache.spark.sql.types.{ArrayType, StringType}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class ExplodeTest extends AnyFlatSpec with Matchers {

  it should "use explode function in select" in {
    val df = Factory.createDf(Map("name" -> StringType, "cities" -> ArrayType(StringType)),
      Row("John", List("London", "Paris")),
      Row("Mary", List("Berlin", "Paris")),
      Row("Mark", null),
      Row("Chad", List()))
    val explodedDf = df.select(
      col("name"),
      explode(col("cities")).alias("city"))
    explodedDf.toJSON.collect should contain inOrderOnly(
      """{"name":"John","city":"London"}""",
      """{"name":"John","city":"Paris"}""",
      """{"name":"Mary","city":"Berlin"}""",
      """{"name":"Mary","city":"Paris"}"""
    )
  }

  it should "use explode function in withColumn" in {
    val df = Factory.createDf(Map("name" -> StringType, "cities" -> ArrayType(StringType)),
      Row("John", List("London", "Paris")),
      Row("Mary", List("Berlin", "Paris")))
    val explodedDf = df.withColumn("city", explode(col("cities")))
    explodedDf.toJSON.collect should contain inOrderOnly(
      """{"name":"John","cities":["London","Paris"],"city":"London"}""",
      """{"name":"John","cities":["London","Paris"],"city":"Paris"}""",
      """{"name":"Mary","cities":["Berlin","Paris"],"city":"Berlin"}""",
      """{"name":"Mary","cities":["Berlin","Paris"],"city":"Paris"}"""
    )
  }

}