package spark3.sql.dataframe.datatype

import org.apache.spark.sql.Row
import org.apache.spark.sql.types.{DoubleType, StringType}
import org.scalatest.flatspec.AnyFlatSpec
import spark3.sql.{Factory, SparkMatchers}

class DoubleTypeTest extends AnyFlatSpec with SparkMatchers {

  it should "DOUBLE column type" in {
    val df = Factory.createDf("name STRING, weight DOUBLE",
      Row("John", 90.5D),
      Row("Mary", 45.6D),
      Row("Mary", null))
    df shouldContain(
      """{"name":"John","weight":90.5}""",
      """{"name":"Mary","weight":45.6}""",
      """{"name":"Mary","weight":null}""")
  }

  it should "DoubleType column type" in {
    val df = Factory.createDf(Map("name" -> StringType, "weight" -> DoubleType),
      Row("John", 90.5D),
      Row("Mary", 45.6D),
      Row("Mary", null))
    df shouldHaveDDL "name STRING,weight DOUBLE"
    df shouldContain(
      """{"name":"John","weight":90.5}""",
      """{"name":"Mary","weight":45.6}""",
      """{"name":"Mary","weight":null}""")
  }

}