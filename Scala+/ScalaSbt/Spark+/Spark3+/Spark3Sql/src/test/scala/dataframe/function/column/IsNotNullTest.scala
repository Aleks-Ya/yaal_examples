package dataframe.function.column

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{col, udf}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class IsNotNullTest extends AnyFlatSpec with Matchers {
  it should "use isNotNull function" in {
    val df = Factory.createDf("country STRING",
      Row("USA"),
      Row("Canada"),
      Row(null),
      Row(""),
    )
    val updatedDf = df.select(
      col("country"),
      col("country").isNotNull as "not_null")
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"country":"USA","not_null":true}""",
      """{"country":"Canada","not_null":true}""",
      """{"country":null,"not_null":false}""",
      """{"country":"","not_null":true}"""
    )
  }

  it should "use isNotNull with None" in {
    val noneUdf = udf((_: String) => None: Option[String])
    val df = Factory.createDf("country STRING", Row("USA"))
    val updatedDf = df.select(
      col("country"),
      noneUdf(col("country")) as "country_none",
      noneUdf(col("country")).isNotNull as "not_null")
    updatedDf.toJSON.collect should contain only """{"country":"USA","country_none":null,"not_null":false}"""
  }
}