package dataframe.datatype

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{col, lit, struct, when}
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class StructTypeTest extends AnyFlatSpec with Matchers {

  it should "create a DataFrame with StructType" in {
    val df = Factory.createDf(Map(
      "name" -> StringType,
      "details" ->
        StructType(Seq(
          StructField("city", StringType),
          StructField("age", IntegerType),
        ))),
      Row("John", Row("London", 30)),
      Row("Mary", Row("Berlin", 15)))
    df.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","details":{"city":"London","age":30}}""",
      """{"name":"Mary","details":{"city":"Berlin","age":15}}"""
    )
  }

  it should "add field to a struct" in {
    val df = Factory.createDf(Map("name" -> StringType, "details" -> StructType(Seq(
      StructField("city", StringType),
      StructField("age", IntegerType),
    ))),
      Row("John", Row("London", 30)),
      Row("Mary", Row("Berlin", 15)))
    val updatedDf = df.withColumn("details", struct(
      col("details.*"),
      when(col("details.age") >= 18, lit(true)).otherwise(lit(false)) as "adult"
    ))
    updatedDf.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","details":{"city":"London","age":30,"adult":true}}""",
      """{"name":"Mary","details":{"city":"Berlin","age":15,"adult":false}}"""
    )
  }

  it should "delete field from a struct" in {
    val df = Factory.createDf(Map("name" -> StringType, "details" -> StructType(Seq(
      StructField("city", StringType),
      StructField("age", IntegerType),
    ))),
      Row("John", Row("London", 30)),
      Row("Mary", Row("Berlin", 15)))
    val updatedDf = df.withColumn("details", col("details").dropFields("age"))
    updatedDf.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","details":{"city":"London"}}""",
      """{"name":"Mary","details":{"city":"Berlin"}}"""
    )
  }

  it should "modify field in a struct" in {
    val df = Factory.createDf(Map("name" -> StringType, "details" -> StructType(Seq(
      StructField("city", StringType),
      StructField("age", IntegerType),
    ))),
      Row("John", Row("London", 30)),
      Row("Mary", Row("Berlin", 15)))
    val updatedDf = df.select(
      col("name"),
      col("details").withField("age", col("details.age") * 2) as "details"
    )
    updatedDf.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","details":{"city":"London","age":60}}""",
      """{"name":"Mary","details":{"city":"Berlin","age":30}}"""
    )
  }

  it should "conditionally modify field in a struct" in {
    val df = Factory.createDf(Map("name" -> StringType, "details" -> StructType(Seq(
      StructField("city", StringType),
      StructField("age", IntegerType),
    ))),
      Row("John", Row("London", 30)),
      Row("Mary", Row("Berlin", 15)))
    val updatedDf = df.select(
      col("name"),
      col("details").withField("age",
        when(col("details.age") > 18, col("details.age") * 2)
          .otherwise(col("details.age") * 3))
        as "details")
    updatedDf.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","details":{"city":"London","age":60}}""",
      """{"name":"Mary","details":{"city":"Berlin","age":45}}"""
    )
  }

}