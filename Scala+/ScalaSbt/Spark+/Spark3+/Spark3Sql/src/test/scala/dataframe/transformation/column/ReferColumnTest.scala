package dataframe.transformation.column

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.util

class ReferColumnTest extends AnyFlatSpec with Matchers {

  it should "col() method" in {
    val df = Factory.peopleDf.withColumn("name_upper", upper(col("name")))
    df.show
    df.printSchema
    df.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","age":25,"gender":"M","name_upper":"JOHN"}""",
      """{"name":"Peter","age":35,"gender":"M","name_upper":"PETER"}""",
      """{"name":"Mary","age":20,"gender":"F","name_upper":"MARY"}"""
    )
  }

  it should "column() method" in {
    val df = Factory.peopleDf.withColumn("name_upper", upper(column("name")))
    df.show
    df.printSchema
    df.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","age":25,"gender":"M","name_upper":"JOHN"}""",
      """{"name":"Peter","age":35,"gender":"M","name_upper":"PETER"}""",
      """{"name":"Mary","age":20,"gender":"F","name_upper":"MARY"}"""
    )
  }

  it should "use $" in {
    import Factory.ss.implicits._
    val df = Factory.peopleDf.withColumn("name_upper", upper($"name"))
    df.show
    df.printSchema
    df.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","age":25,"gender":"M","name_upper":"JOHN"}""",
      """{"name":"Peter","age":35,"gender":"M","name_upper":"PETER"}""",
      """{"name":"Mary","age":20,"gender":"F","name_upper":"MARY"}"""
    )
  }

  it should "use DataFrame" in {
    val df1 = Factory.peopleDf
    val df2 = df1.withColumn("name_upper", upper(df1("name")))
    df2.show
    df2.printSchema
    df2.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","age":25,"gender":"M","name_upper":"JOHN"}""",
      """{"name":"Peter","age":35,"gender":"M","name_upper":"PETER"}""",
      """{"name":"Mary","age":20,"gender":"F","name_upper":"MARY"}"""
    )
  }

  it should "getField from a column" in {
    val schema = StructType(
      StructField("name", StringType) ::
        StructField("details", StructType(Array(
          StructField("age", IntegerType),
          StructField("gender", StringType)
        )))
        :: Nil)
    val rows = util.Arrays.asList(
      Row("John", Row(30, "M")),
      Row("Mary", Row(25, "F")))
    val df = Factory.ss.createDataFrame(rows, schema)
      .withColumn("details_oneline", concat_ws("-",
        col("details").getField("gender"),
        col("details").getField("age")))
    df.show
    df.printSchema
    df.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","details":{"age":30,"gender":"M"},"details_oneline":"M-30"}""",
      """{"name":"Mary","details":{"age":25,"gender":"F"},"details_oneline":"F-25"}"""
    )
  }

}