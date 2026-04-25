package spark3.sql.dataframe.create.schema

import org.apache.spark.sql.Row
import org.apache.spark.sql.types._
import org.scalatest.flatspec.AnyFlatSpec
import spark3.sql.{Factory, SparkMatchers}

import java.sql.{Date, Timestamp}
import java.util

class StructTypeTest extends AnyFlatSpec with SparkMatchers {

  it should "all possible field types" in {
    val schema = StructType(Seq(
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
      StructField("arrayPrimitiveField", ArrayType(IntegerType)),
      StructField("arrayStructField", ArrayType(StructType(Seq(
        StructField("arrayNestedField1", StringType),
        StructField("arrayNestedField2", IntegerType)
      )))),
      StructField("mapField", MapType(StringType, IntegerType)),
      StructField("structField", StructType(Seq(
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
      Seq(Row("A", 11), Row("B", 22)),
      Map("one" -> 1, "two" -> 2, "three" -> 3),
      Row("Nested value"),
      BigDecimal(123.45)
    )
    val df = Factory.ss.createDataFrame(util.Arrays.asList(row), schema)
    df.printSchema()
    df.show()
    df.schema.simpleString shouldEqual "struct<intField:int,longField:bigint,doubleField:double,floatField:float,stringField:string,booleanField:boolean,byteField:tinyint,shortField:smallint,binaryField:binary,timestampField:timestamp,dateField:date,arrayPrimitiveField:array<int>,arrayStructField:array<struct<arrayNestedField1:string,arrayNestedField2:int>>,mapField:map<string,int>,structField:struct<nestedField:string>,decimalField:decimal(10,2)>"
    df shouldContain """{"intField":42,"longField":1234567890123,"doubleField":42.42,"floatField":23.23,"stringField":"Hello, Spark!","booleanField":true,"byteField":8,"shortField":16,"binaryField":"AQIDBA==","timestampField":"1970-01-20T22:02:27.963+07:00","dateField":"1970-01-20","arrayPrimitiveField":[1,2,3,4],"arrayStructField":[{"arrayNestedField1":"A","arrayNestedField2":11},{"arrayNestedField1":"B","arrayNestedField2":22}],"mapField":{"one":1,"two":2,"three":3},"structField":{"nestedField":"Nested value"},"decimalField":123.45}"""
  }
}