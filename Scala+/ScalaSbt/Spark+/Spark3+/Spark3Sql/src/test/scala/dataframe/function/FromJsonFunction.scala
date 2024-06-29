package dataframe.function

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{col, from_json}
import org.apache.spark.sql.types.{BooleanType, IntegerType, StringType, StructType}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class FromJsonFunction extends AnyFlatSpec with Matchers {
  it should "use from_json function" in {
    val personSchema = new StructType()
      .add("name", StringType)
      .add("details", StringType)
    val df = Factory.createDf(personSchema,
      Row("John", """{ "age": 30, "male": true }"""),
      Row("Mary", """{ "age": 25, "male": false }"""),
      Row("Peter", """{ invalid JSON }""")
    )

    val detailsSchema = new StructType()
      .add("age", IntegerType)
      .add("male", BooleanType)
    val updatedDf = df.select(
      col("name"),
      from_json(col("details"), detailsSchema) as "details"
    )

    updatedDf.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","details":{"age":30,"male":true}}""",
      """{"name":"Mary","details":{"age":25,"male":false}}""",
      """{"name":"Peter","details":{"age":null,"male":null}}"""
    )
  }
}