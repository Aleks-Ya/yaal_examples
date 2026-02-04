package dataframe.udf

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class UdfReturnsStructTest extends AnyFlatSpec with Matchers {

  it should "UDF returns a struct (cast to schema)" in {
    val stringCasesUdf = udf((str: String) => {
      val upperCase = str.toUpperCase
      val lowerCase = str.toLowerCase
      (upperCase, lowerCase)
    })
    val schema = StructType.fromDDL("upper STRING,lower STRING")
    val df = Factory.createDf("name STRING", Row("John"), Row("Mary"))
    val updatedDf = df.withColumn("name_cases", stringCasesUdf(col("name")).cast(schema))
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"name":"John","name_cases":{"upper":"JOHN","lower":"john"}}""",
      """{"name":"Mary","name_cases":{"upper":"MARY","lower":"mary"}}"""
    )
  }

  it should "UDF returns a struct (return an object)" in {
    val stringCasesUdf = udf((str: String) => {
      val upperCase = str.toUpperCase
      val lowerCase = str.toLowerCase
      Cases(upperCase, lowerCase)
    })
    val df = Factory.createDf("name STRING", Row("John"), Row("Mary"))
    val updatedDf = df.withColumn("name_cases", stringCasesUdf(col("name")))
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"name":"John","name_cases":{"upper":"JOHN","lower":"john"}}""",
      """{"name":"Mary","name_cases":{"upper":"MARY","lower":"mary"}}"""
    )
  }


  it should "UDF returns a struct (deprecated)" in {
    Factory.ss.conf.set("spark.sql.legacy.allowUntypedScalaUDF", "true")

    val schema = StructType(Seq(
      StructField("upper", StringType, nullable = false),
      StructField("lower", StringType, nullable = false)
    ))

    val stringCasesUdf = udf((str: String) => {
      val upperCase = str.toUpperCase
      val lowerCase = str.toLowerCase
      Row(upperCase, lowerCase)
    }, schema)

    val df = Factory.createDf("name STRING", Row("John"), Row("Mary"))
    val updatedDf = df.withColumn("name_cases", stringCasesUdf(col("name")))

    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"name":"John","name_cases":{"upper":"JOHN","lower":"john"}}""",
      """{"name":"Mary","name_cases":{"upper":"MARY","lower":"mary"}}"""
    )
  }
}

private case class Cases(upper: String, lower: String)