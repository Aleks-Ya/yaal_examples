package spark3.sql.dataframe.datatype

import org.apache.spark.sql.Row
import org.apache.spark.sql.types.{DoubleType, StringType}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark3.sql.Factory

class DoubleTypeTest extends AnyFlatSpec with Matchers {

  it should "DOUBLE column type" in {
    val df = Factory.createDf("name STRING, weight DOUBLE",
      Row("John", 90.5D),
      Row("Mary", 45.6D))
    df.toJSON.collect should contain inOrderOnly(
      """{"name":"John","weight":90.5}""",
      """{"name":"Mary","weight":45.6}""")
  }

  it should "IntegerType column type" in {
    val df = Factory.createDf(Map("name" -> StringType, "weight" -> DoubleType),
      Row("John", 90.5D),
      Row("Mary", 45.6D))
    df.toJSON.collect should contain inOrderOnly(
      """{"name":"John","weight":90.5}""",
      """{"name":"Mary","weight":45.6}""")
  }

}