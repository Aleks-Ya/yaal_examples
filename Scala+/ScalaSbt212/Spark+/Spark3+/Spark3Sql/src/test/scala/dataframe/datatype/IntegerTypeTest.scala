package dataframe.datatype

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.types.{IntegerType, StringType}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class IntegerTypeTest extends AnyFlatSpec with Matchers {

  it should "INT column type" in {
    val df = Factory.createDf("name STRING, age INT",
      Row("John", 30),
      Row("Mary", 25))
    df.toJSON.collect should contain inOrderOnly(
      """{"name":"John","age":30}""",
      """{"name":"Mary","age":25}""")
  }

  it should "IntegerType column type" in {
    val df = Factory.createDf(Map("name" -> StringType, "age" -> IntegerType),
      Row("John", 30),
      Row("Mary", 25))
    df.toJSON.collect should contain inOrderOnly(
      """{"name":"John","age":30}""",
      """{"name":"Mary","age":25}""")
  }

}