package dataframe.transformation.column

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.{ByteType, IntegerType, StringType}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ChangeColumnTypeTest extends AnyFlatSpec with Matchers {

  it should "cast to related type" in {
    val df = Factory.peopleDf
    df.schema.simpleString shouldEqual "struct<name:string,age:int,gender:string>"

    val newDf = df.withColumn("age", col("age").cast(ByteType))
    newDf.printSchema
    newDf.schema.simpleString shouldEqual "struct<name:string,age:tinyint,gender:string>"
  }

  it should "cast Number to String" in {
    val df = Factory.peopleDf
    df.schema.simpleString shouldEqual "struct<name:string,age:int,gender:string>"

    val newDf = df.withColumn("age", col("age").cast(StringType))
    newDf.printSchema
    newDf.schema.simpleString shouldEqual "struct<name:string,age:string,gender:string>"
  }

  it should "cast String to Number" in {
    val df = Factory.createDf(Map("name" -> StringType, "age" -> StringType),
      Row("John", "25"), Row("Peter", "35"), Row("Mary", "20"))
    df.schema.simpleString shouldEqual "struct<name:string,age:string>"

    val newDf = df.withColumn("age", col("age").cast(IntegerType))
    newDf.printSchema
    newDf.schema.simpleString shouldEqual "struct<name:string,age:int>"
  }
}