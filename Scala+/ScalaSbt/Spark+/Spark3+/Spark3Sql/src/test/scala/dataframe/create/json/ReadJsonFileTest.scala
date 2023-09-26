package dataframe.create.json

import factory.Factory
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ReadJsonFileTest extends AnyFlatSpec with Matchers {

  it should "read several JSONs from NDJSON-file (infer schema)" in {
    val file = getClass.getResource("ReadJsonFileTest.ndjson")
    file should not be null

    val df = Factory.ss.read.json(file.getPath)
    df.printSchema()
    df.show()
    df.schema.simpleString shouldEqual "struct<age:bigint,gender:string,name:string>"
    df.toJSON.collect() should contain inOrderOnly(
      """{"age":30,"gender":"M","name":"John"}""",
      """{"age":25,"gender":"F","name":"Mary"}"""
    )
  }

  it should "read single JSONs with an array (infer schema)" in {
    val file = getClass.getResource("ReadJsonFileTest.json")
    file should not be null

    val df = Factory.ss.read.json(file.getPath)
    df.printSchema()
    df.show()
    df.schema.simpleString shouldEqual "struct<age:bigint,gender:string,name:string>"
    df.toJSON.collect() should contain inOrderOnly(
      """{"age":30,"gender":"M","name":"John"}""",
      """{"age":25,"gender":"F","name":"Mary"}"""
    )
  }

  it should "read several JSONs from NDJSON-file (schema is provided)" in {
    val file = getClass.getResource("ReadJsonFileTest.ndjson")
    file should not be null

    val schema = StructType(Array(
      StructField("name", StringType),
      StructField("age", IntegerType),
      StructField("gender", StringType)
    ))
    val df = Factory.ss.read.schema(schema).json(file.getPath)
    df.printSchema()
    df.show()
    df.schema.simpleString shouldEqual "struct<name:string,age:int,gender:string>"
    df.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","age":30,"gender":"M"}""",
      """{"name":"Mary","age":25,"gender":"F"}"""
    )
  }

}