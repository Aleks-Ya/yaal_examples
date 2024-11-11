package dataframe.transformation.column

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ReferNestedColumnTest extends AnyFlatSpec with Matchers {

  it should "get field from a column using getField" in {
    val df = Factory.createDf("name STRING,details STRUCT<age: INT, gender: STRING>",
        Row("John", Row(30, "M")),
        Row("Mary", Row(25, "F")))
      .withColumn("details_oneline", concat_ws("-",
        col("details").getField("gender"),
        col("details").getField("age")))
    df.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","details":{"age":30,"gender":"M"},"details_oneline":"M-30"}""",
      """{"name":"Mary","details":{"age":25,"gender":"F"},"details_oneline":"F-25"}""")
  }

  it should "get field from a column using parentheses" in {
    val df = Factory.createDf("name STRING,details STRUCT<age: INT, gender: STRING>",
        Row("John", Row(30, "M")),
        Row("Mary", Row(25, "F")))
      .withColumn("details_oneline", concat_ws("-",
        col("details")("gender"),
        col("details")("age")))
    df.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","details":{"age":30,"gender":"M"},"details_oneline":"M-30"}""",
      """{"name":"Mary","details":{"age":25,"gender":"F"},"details_oneline":"F-25"}""")
  }

  it should "get field from a column using dot" in {
    val df = Factory.createDf("name STRING,details STRUCT<age: INT, gender: STRING>",
        Row("John", Row(30, "M")),
        Row("Mary", Row(25, "F")))
      .withColumn("details_oneline", concat_ws("-",
        col("details.gender"),
        col("details.age")))
    df.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","details":{"age":30,"gender":"M"},"details_oneline":"M-30"}""",
      """{"name":"Mary","details":{"age":25,"gender":"F"},"details_oneline":"F-25"}""")
  }

}