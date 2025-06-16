package dataframe.udf

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class OneLineUdfTest extends AnyFlatSpec with Matchers {
  it should "declare an one-line UDF" in {
    val df = Factory.createDf("name STRING", Row("John"), Row("Mary"))
    val upperCaseUdf = udf((str: String) => str.toUpperCase)
    val updatedDf = df.withColumn("upper_name", upperCaseUdf(col("name")))
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"name":"John","upper_name":"JOHN"}""",
      """{"name":"Mary","upper_name":"MARY"}"""
    )
  }

  it should "declare a UDF with explicit types" in {
    val df = Factory.createDf("name STRING", Row("John"), Row("Mary"))
    val upperCaseUdf = udf[String, String]((str: String) => str.toUpperCase)
    val updatedDf = df.withColumn("upper_name", upperCaseUdf(col("name")))
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"name":"John","upper_name":"JOHN"}""",
      """{"name":"Mary","upper_name":"MARY"}"""
    )
  }
}