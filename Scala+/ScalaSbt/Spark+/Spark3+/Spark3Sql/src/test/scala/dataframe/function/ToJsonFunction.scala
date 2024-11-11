package dataframe.function

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{col, to_json}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ToJsonFunction extends AnyFlatSpec with Matchers {
  it should "convert column value to JSON" in {
    val df = Factory.createDf("name STRING,details STRUCT<city: STRING, age: INT>",
      Row("John", Row("London", 30)),
      Row("Mary", Row("Berlin", 15)))
    val updatedDf = df.select(
      col("name"),
      to_json(col("details")) as "json"
    )
    updatedDf.schema.toDDL shouldEqual "name STRING,json STRING"
    updatedDf.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","json":"{\"city\":\"London\",\"age\":30}"}""",
      """{"name":"Mary","json":"{\"city\":\"Berlin\",\"age\":15}"}"""
    )
  }
}