package spark4.sql.dataframe.function.builtin.json

import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{col, to_json}
import org.scalatest.flatspec.AnyFlatSpec
import spark4.sql.{Factory, SparkMatchers}

class ToJsonTest extends AnyFlatSpec with SparkMatchers {
  it should "convert column value to JSON" in {
    val df = Factory.createDf("name STRING,details STRUCT<city: STRING, age: INT>",
      Row("John", Row("London", 30)),
      Row("Mary", Row("Berlin", 15)))
    val updatedDf = df.select(
      col("name"),
      to_json(col("details")) as "json"
    )
    updatedDf shouldHaveDDL "name STRING,json STRING"
    updatedDf shouldContain(
      """{"name":"John","json":"{\"city\":\"London\",\"age\":30}"}""",
      """{"name":"Mary","json":"{\"city\":\"Berlin\",\"age\":15}"}"""
    )
  }
}