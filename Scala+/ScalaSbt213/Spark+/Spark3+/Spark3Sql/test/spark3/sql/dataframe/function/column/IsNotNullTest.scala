package spark3.sql.dataframe.function.column

import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{col, udf}
import org.scalatest.flatspec.AnyFlatSpec
import spark3.sql.{Factory, SparkMatchers}

class IsNotNullTest extends AnyFlatSpec with SparkMatchers {

  it should "use isNotNull function" in {
    val df = Factory.createDf("country STRING",
      Row("USA"),
      Row("Canada"),
      Row(null),
      Row(""))
    val updatedDf = df.select(
      col("country"),
      col("country").isNotNull as "not_null")
    updatedDf shouldHaveDDL "country STRING,not_null BOOLEAN NOT NULL"
    updatedDf shouldContain(
      """{"country":"USA","not_null":true}""",
      """{"country":"Canada","not_null":true}""",
      """{"country":null,"not_null":false}""",
      """{"country":"","not_null":true}""")
  }

  it should "use isNotNull with None" in {
    val noneUdf = udf((_: String) => None: Option[String])
    val df = Factory.createDf("country STRING",
      Row("USA"))
    val updatedDf = df.select(
      col("country"),
      noneUdf(col("country")) as "country_none",
      noneUdf(col("country")).isNotNull as "not_null")
    updatedDf shouldHaveDDL "country STRING,country_none STRING,not_null BOOLEAN NOT NULL"
    updatedDf shouldContain """{"country":"USA","country_none":null,"not_null":false}"""
  }

}