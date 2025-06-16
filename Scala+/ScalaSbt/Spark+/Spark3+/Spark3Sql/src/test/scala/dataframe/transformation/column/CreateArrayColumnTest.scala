package dataframe.transformation.column

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.types._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class CreateArrayColumnTest extends AnyFlatSpec with Matchers {
  it should "create array StructureType" in {
    val df = Factory.createDf(Map("name" -> ArrayType(IntegerType)), Row(Array(1, 2, 3)), Row(Array(4, 5, 6)))
    df.schema.simpleString shouldEqual "struct<name:array<int>>"
    df.toJSON.collect should contain inOrderOnly(
      """{"name":[1,2,3]}""",
      """{"name":[4,5,6]}"""
    )
  }
}