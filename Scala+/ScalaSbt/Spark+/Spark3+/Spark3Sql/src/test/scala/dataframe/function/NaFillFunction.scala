package dataframe.function

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.types.{BooleanType, IntegerType, StringType}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class NaFillFunction extends AnyFlatSpec with Matchers {
  it should "fill missing values" in {
    val df = Factory.createDf(Map("person" -> StringType, "age" -> IntegerType,
      "married" -> BooleanType, "city" -> StringType),
      Row("John", 35, null, "Berlin"),
      Row("Mary", null, true, null),
      Row("Mark", null, null, null),
    )
    val updatedDf = df
      .na.fill(18)
      .na.fill(false)
      .na.fill("London", Seq("city"))
    updatedDf.toJSON.collect() should contain inOrderOnly(
      """{"person":"John","age":35,"married":false,"city":"Berlin"}""",
      """{"person":"Mary","age":18,"married":true,"city":"London"}""",
      """{"person":"Mark","age":18,"married":false,"city":"London"}"""
    )
  }
}