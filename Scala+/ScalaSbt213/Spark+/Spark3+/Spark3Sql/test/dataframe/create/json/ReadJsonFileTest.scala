package dataframe.create.json

import factory.Factory
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.util.Objects.requireNonNull

class ReadJsonFileTest extends AnyFlatSpec with Matchers {

  it should "read several JSONs from NDJSON-file (infer schema)" in {
    val file = requireNonNull(getClass.getResource("ReadJsonFileTest.ndjson"))
    val df = Factory.ss.read.json(file.getPath)
    df.printSchema()
    df.show()
    df.schema.simpleString shouldEqual "struct<age:bigint,gender:string,name:string>"
    df.toJSON.collect should contain inOrderOnly(
      """{"age":30,"gender":"M","name":"John"}""",
      """{"age":25,"gender":"F","name":"Mary"}"""
    )
  }

  it should "read single JSONs with an array (infer schema)" in {
    val file = requireNonNull(getClass.getResource("ReadJsonFileTest.json"))
    val df = Factory.ss.read.json(file.getPath)
    df.printSchema()
    df.show()
    df.schema.simpleString shouldEqual "struct<age:bigint,gender:string,name:string>"
    df.toJSON.collect should contain inOrderOnly(
      """{"age":30,"gender":"M","name":"John"}""",
      """{"age":25,"gender":"F","name":"Mary"}"""
    )
  }

  it should "read several JSONs from NDJSON-file (schema is provided)" in {
    val file = requireNonNull(getClass.getResource("ReadJsonFileTest.ndjson"))
    val schema = StructType(Array(
      StructField("name", StringType),
      StructField("age", IntegerType),
      StructField("gender", StringType)
    ))
    val df = Factory.ss.read.schema(schema).json(file.getPath)
    df.printSchema()
    df.show()
    df.schema.simpleString shouldEqual "struct<name:string,age:int,gender:string>"
    df.toJSON.collect should contain inOrderOnly(
      """{"name":"John","age":30,"gender":"M"}""",
      """{"name":"Mary","age":25,"gender":"F"}"""
    )
  }

  /**
   * Create the archive: "gzip -k ReadJsonFileTest.json"
   */
  it should "read a compressed JSON file (GZIP)" in {
    val file = requireNonNull(getClass.getResource("ReadJsonFileTest.json.gz"))
    val df = Factory.ss.read
      .option("compression", "gzip")
      .json(file.getPath)
    df.toJSON.collect should contain inOrderOnly(
      """{"age":30,"gender":"M","name":"John"}""",
      """{"age":25,"gender":"F","name":"Mary"}"""
    )
  }

  it should "read several JSON files (infer schema)" in {
    val file1 = requireNonNull(getClass.getResource("ReadJsonFileTest.json"))
    val file2 = requireNonNull(getClass.getResource("ReadJsonFileTest2.json"))
    val df = Factory.ss.read.json(file1.getPath, file2.getPath)
    df.printSchema()
    df.show()
    df.schema.simpleString shouldEqual "struct<age:bigint,gender:string,name:string>"
    df.toJSON.collect should contain inOrderOnly(
      """{"age":30,"gender":"M","name":"John"}""",
      """{"age":25,"gender":"F","name":"Mary"}""",
      """{"age":20,"gender":"M","name":"Mark"}""",
      """{"age":15,"gender":"M","name":"Chad"}"""
    )
  }

}