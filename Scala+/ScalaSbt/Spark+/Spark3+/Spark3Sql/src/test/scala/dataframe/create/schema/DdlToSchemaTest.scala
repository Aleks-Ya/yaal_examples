package dataframe.create.schema

import org.apache.spark.sql.types._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class DdlToSchemaTest extends AnyFlatSpec with Matchers {

  it should "read DDL with line breaks" in {
    val multiLinesDdl =
      """name STRING,
        |details STRUCT<
        |  city: STRING,
        |  age: INT,
        |  phones: ARRAY<STRING>
        |>""".stripMargin
    val multiLinesSchema = StructType.fromDDL(multiLinesDdl)
    val oneLineDdl = "name STRING,details STRUCT<city: STRING, age: INT, phones: ARRAY<STRING>>"
    val oneLineSchema = StructType.fromDDL(oneLineDdl)
    multiLinesSchema shouldEqual oneLineSchema
  }

  it should "read Array of Struct" in {
    val ddl = "people ARRAY<STRUCT<city: STRING, age: INT>>"
    val schema = StructType.fromDDL(ddl)
    schema shouldEqual StructType(Array(
      StructField("people", ArrayType(
        StructType(Array(
          StructField("city", StringType),
          StructField("age", IntegerType)
        ))
      ))))
  }

}