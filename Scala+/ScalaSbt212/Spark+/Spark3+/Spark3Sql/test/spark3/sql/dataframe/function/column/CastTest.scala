package spark3.sql.dataframe.function.column

import org.apache.spark.sql.Row
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark3.sql.Factory

class CastTest extends AnyFlatSpec with Matchers {

  it should "cast to related type" in {
    val df = Factory.peopleDf
    df.schema.toDDL shouldEqual "name STRING,age INT,gender STRING"
    val updatedDf = df.withColumn("age", col("age").cast(ByteType))
    updatedDf.schema.toDDL shouldEqual "name STRING,age TINYINT,gender STRING"
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"name":"John","age":25,"gender":"M"}""",
      """{"name":"Peter","age":35,"gender":"M"}""",
      """{"name":"Mary","age":20,"gender":"F"}"""
    )
  }

  it should "cast Number to String" in {
    val df = Factory.peopleDf
    df.schema.toDDL shouldEqual "name STRING,age INT,gender STRING"
    val updatedDf = df.withColumn("age", col("age").cast(StringType))
    updatedDf.schema.toDDL shouldEqual "name STRING,age STRING,gender STRING"
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"name":"John","age":"25","gender":"M"}""",
      """{"name":"Peter","age":"35","gender":"M"}""",
      """{"name":"Mary","age":"20","gender":"F"}"""
    )
  }

  it should "cast String to Number" in {
    val df = Factory.createDf("name STRING, age STRING",
      Row("John", "25"), Row("Peter", "35"), Row("Mary", "20"))
    val updatedDf = df.withColumn("age", col("age").cast(IntegerType))
    updatedDf.schema.toDDL shouldEqual "name STRING,age INT"
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"name":"John","age":25}""",
      """{"name":"Peter","age":35}""",
      """{"name":"Mary","age":20}"""
    )
  }

  it should "cast a struct to DDL" in {
    val df = Factory.createDf("name STRING, details STRUCT<age: INT, gender: STRING>",
      Row("John", Row(25, "M")),
      Row("Mary", Row(20, "F")),
      Row("Mark", Row(null, null)),
      Row("Matt", null)
    )
    val targetDdl = "STRUCT<age: STRING, gender: STRING>"
    val updatedDf = df.withColumn("details", col("details").cast(targetDdl))
    updatedDf.schema.toDDL shouldEqual "name STRING,details STRUCT<age: STRING, gender: STRING>"
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"name":"John","details":{"age":"25","gender":"M"}}""",
      """{"name":"Mary","details":{"age":"20","gender":"F"}}""",
      """{"name":"Mark","details":{"age":null,"gender":null}}""",
      """{"name":"Matt","details":null}"""
    )
  }

  it should "cast a struct to StructType" in {
    val df = Factory.createDf("name STRING, details STRUCT<age: INT, gender: STRING>",
      Row("John", Row(25, "M")),
      Row("Mary", Row(20, "F")),
      Row("Mark", Row(null, null)),
      Row("Matt", null)
    )
    val targetSchema = StructType(Seq(
      StructField("age", StringType, nullable = true),
      StructField("gender", StringType, nullable = true)
    ))
    val updatedDf = df.withColumn("details", col("details").cast(targetSchema))
    updatedDf.schema.toDDL shouldEqual "name STRING,details STRUCT<age: STRING, gender: STRING>"
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"name":"John","details":{"age":"25","gender":"M"}}""",
      """{"name":"Mary","details":{"age":"20","gender":"F"}}""",
      """{"name":"Mark","details":{"age":null,"gender":null}}""",
      """{"name":"Matt","details":null}"""
    )
  }

  it should "cast a field in a struct" in {
    val df = Factory.createDf("name STRING, details STRUCT<age: INT, gender: STRING>",
      Row("John", Row(25, "M")),
      Row("Mary", Row(20, "F")),
      Row("Mark", Row(null, null)),
      Row("Matt", null)
    )
    val detailsCol = col("details").withField("age", col("details").getField("age").cast(StringType))
    val updatedDf = df.withColumn("details", detailsCol)
    updatedDf.schema.toDDL shouldEqual "name STRING,details STRUCT<age: STRING, gender: STRING>"
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"name":"John","details":{"age":"25","gender":"M"}}""",
      """{"name":"Mary","details":{"age":"20","gender":"F"}}""",
      """{"name":"Mark","details":{"age":null,"gender":null}}""",
      """{"name":"Matt","details":null}"""
    )
  }

}