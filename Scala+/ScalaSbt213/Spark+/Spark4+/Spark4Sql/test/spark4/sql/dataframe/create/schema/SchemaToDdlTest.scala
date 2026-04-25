package spark4.sql.dataframe.create.schema

import org.apache.spark.sql.types._
import org.scalatest.flatspec.AnyFlatSpec
import spark4.sql.SparkMatchers

class SchemaToDdlTest extends AnyFlatSpec with SparkMatchers {

  it should "convert a schema to DDL and back" in {
    val schemaExp = StructType(Seq(
      StructField("name", StringType),
      StructField("details", StructType(Seq(
        StructField("city", StringType),
        StructField("age", IntegerType),
        StructField("phones", ArrayType(StringType))
      )))))
    val ddl = schemaExp.toDDL
    ddl shouldEqual "name STRING,details STRUCT<city: STRING, age: INT, phones: ARRAY<STRING>>"

    val schemaAct = StructType.fromDDL(ddl)
    schemaAct shouldEqual schemaExp
  }

}