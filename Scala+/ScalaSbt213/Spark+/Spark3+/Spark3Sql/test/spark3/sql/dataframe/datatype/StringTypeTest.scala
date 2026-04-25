package spark3.sql.dataframe.datatype

import org.apache.spark.sql.Row
import org.apache.spark.sql.types.StringType
import org.scalatest.flatspec.AnyFlatSpec
import spark3.sql.{Factory, SparkMatchers}

class StringTypeTest extends AnyFlatSpec with SparkMatchers {

  it should "use STRING column type" in {
    val df = Factory.createDf("name STRING, city STRING",
      Row("John", "London"),
      Row("Mary", "Paris"))
    df shouldContain(
      """{"name":"John","city":"London"}""",
      """{"name":"Mary","city":"Paris"}""")
  }

  it should "StringType column type" in {
    val df = Factory.createDf(Map("name" -> StringType, "city" -> StringType),
      Row("John", "London"),
      Row("Mary", "Paris"))
    df shouldHaveDDL "name STRING,city STRING"
    df shouldContain(
      """{"name":"John","city":"London"}""",
      """{"name":"Mary","city":"Paris"}""")
  }

}