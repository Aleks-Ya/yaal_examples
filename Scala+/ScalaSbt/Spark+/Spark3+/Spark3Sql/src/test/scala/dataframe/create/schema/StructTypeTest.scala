package dataframe.create.schema

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.types._
import org.scalatest.BeforeAndAfterAll
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.sql.{Date, Timestamp}
import java.util

class StructTypeTest extends AnyFlatSpec with BeforeAndAfterAll with Matchers {

  it should "all possible field types" in {
    val schema = StructType(Array(
      StructField("intField", IntegerType),
      StructField("longField", LongType),
      StructField("doubleField", DoubleType),
      StructField("floatField", FloatType),
      StructField("stringField", StringType),
      StructField("booleanField", BooleanType),
      StructField("byteField", ByteType),
      StructField("shortField", ShortType),
      StructField("binaryField", BinaryType),
      StructField("timestampField", TimestampType),
      StructField("dateField", DateType),
      StructField("arrayField", ArrayType(IntegerType)),
      StructField("mapField", MapType(StringType, IntegerType)),
      StructField("structField", StructType(Array(
        StructField("nestedField", StringType)
      ))),
      StructField("decimalField", DecimalType(10, 2))
    ))

    val row = Row(
      42,
      1234567890123L,
      42.42,
      23.23f,
      "Hello, Spark!",
      true,
      8.toByte,
      16.toShort,
      Array[Byte](1, 2, 3, 4),
      new Timestamp(1695747963),
      new Date(1695747963),
      Seq(1, 2, 3, 4),
      Map("one" -> 1, "two" -> 2, "three" -> 3),
      Row("Nested value"),
      BigDecimal(123.45)
    )
    val df = Factory.ss.createDataFrame(util.Arrays.asList(row), schema)
    df.printSchema()
    df.show()
    df.schema.simpleString shouldEqual "struct<intField:int,longField:bigint,doubleField:double,floatField:float,stringField:string,booleanField:boolean,byteField:tinyint,shortField:smallint,binaryField:binary,timestampField:timestamp,dateField:date,arrayField:array<int>,mapField:map<string,int>,structField:struct<nestedField:string>,decimalField:decimal(10,2)>"
    df.toJSON.collect() should contain only """{"intField":42,"longField":1234567890123,"doubleField":42.42,"floatField":23.23,"stringField":"Hello, Spark!","booleanField":true,"byteField":8,"shortField":16,"binaryField":"AQIDBA==","timestampField":"1970-01-20T23:02:27.963+08:00","dateField":"1970-01-20","arrayField":[1,2,3,4],"mapField":{"one":1,"two":2,"three":3},"structField":{"nestedField":"Nested value"},"decimalField":123.45}"""
  }
}