package spark3.sql.dataframe.function.column

import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{col, udf}
import org.scalatest.flatspec.AnyFlatSpec
import spark3.sql.{Factory, SparkMatchers}

class IsNullTest extends AnyFlatSpec with SparkMatchers {
  it should "use isNull function" in {
    val df = Factory.createDf("country STRING",
      Row("USA"),
      Row("Canada"),
      Row(null),
      Row(""),
    )
    val updatedDf = df.select(
      col("country"),
      col("country").isNull as "is_null")
    updatedDf shouldContain(
      """{"country":"USA","is_null":false}""",
      """{"country":"Canada","is_null":false}""",
      """{"country":null,"is_null":true}""",
      """{"country":"","is_null":false}"""
    )
  }

  it should "use isNull with None" in {
    val noneUdf = udf((_: String) => None: Option[String])
    val df = Factory.createDf("country STRING", Row("USA"))
    val updatedDf = df.select(
      col("country"),
      noneUdf(col("country")) as "country_none",
      noneUdf(col("country")).isNull as "is_null")
    updatedDf shouldContain """{"country":"USA","country_none":null,"is_null":true}"""
  }
}