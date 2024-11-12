package dataframe.function.builtin

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{col, explode_outer}
import org.apache.spark.sql.types.{ArrayType, StringType}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class ExplodeOuterFunction extends AnyFlatSpec with Matchers {
  it should "use explode_outer function" in {
    val df = Factory.createDf(Map("name" -> StringType, "cities" -> ArrayType(StringType)),
      Row("John", List("London", "Paris")),
      Row("Mary", List("Berlin", "Paris")),
      Row("Mark", null),
      Row("Chad", List())
    )
    val explodedDf = df.select(col("name"), explode_outer(col("cities")).alias("city"))
    explodedDf.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","city":"London"}""",
      """{"name":"John","city":"Paris"}""",
      """{"name":"Mary","city":"Berlin"}""",
      """{"name":"Mary","city":"Paris"}""",
      """{"name":"Mark","city":null}""",
      """{"name":"Chad","city":null}"""
    )
  }
}