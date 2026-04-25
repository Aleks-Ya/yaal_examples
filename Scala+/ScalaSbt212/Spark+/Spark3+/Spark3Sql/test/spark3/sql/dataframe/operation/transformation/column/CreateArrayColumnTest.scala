package spark3.sql.dataframe.operation.transformation.column

import org.apache.spark.sql.Row
import org.apache.spark.sql.types._
import org.scalatest.flatspec.AnyFlatSpec
import spark3.sql.{Factory, SparkMatchers}

class CreateArrayColumnTest extends AnyFlatSpec with SparkMatchers {
  it should "create array StructureType" in {
    val df = Factory.createDf(Map("name" -> ArrayType(IntegerType)), Row(Array(1, 2, 3)), Row(Array(4, 5, 6)))
    df.schema.simpleString shouldEqual "struct<name:array<int>>"
    df shouldContain(
      """{"name":[1,2,3]}""",
      """{"name":[4,5,6]}"""
    )
  }
}