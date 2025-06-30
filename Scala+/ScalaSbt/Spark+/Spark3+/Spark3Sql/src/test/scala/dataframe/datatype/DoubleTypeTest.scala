package dataframe.datatype

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.types.{DoubleType, StringType}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

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