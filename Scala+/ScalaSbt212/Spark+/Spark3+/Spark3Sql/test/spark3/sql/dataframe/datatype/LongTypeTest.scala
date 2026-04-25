package spark3.sql.dataframe.datatype

import org.apache.spark.sql.Row
import org.apache.spark.sql.types.{LongType, StringType}
import org.scalatest.flatspec.AnyFlatSpec
import spark3.sql.{Factory, SparkMatchers}

class LongTypeTest extends AnyFlatSpec with SparkMatchers {

  it should "BIGINT column type" in {
    val df = Factory.createDf("name STRING, weight BIGINT",
      Row("John", 90L),
      Row("Mary", 45L))
    df shouldContain(
      """{"name":"John","weight":90}""",
      """{"name":"Mary","weight":45}""")
  }

  it should "LongType column type" in {
    val df = Factory.createDf(Map("name" -> StringType, "weight" -> LongType),
      Row("John", 90L),
      Row("Mary", 45L))
    df shouldHaveDDL "name STRING,weight BIGINT"
    df shouldContain(
      """{"name":"John","weight":90}""",
      """{"name":"Mary","weight":45}""")
  }

}