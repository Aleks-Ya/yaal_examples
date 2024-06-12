package dataframe.function

import factory.Factory
import org.apache.spark.sql.functions.{col, transform, udf}
import org.apache.spark.sql.types.{ArrayType, StringType}
import org.apache.spark.sql.{Column, Row}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class TransformFunction extends AnyFlatSpec with Matchers {
  it should "use transform function" in {
    val df = Factory.createDf(Map("cities" -> ArrayType(StringType)),
      Row(List("London", "Paris", "London")),
      Row(List("Berlin", "Barcelona", "Barcelona")))
    val updatedDf = df.select(
      col("cities"),
      transform(col("cities"), city => UpperCaseUdf(city)) as "upper_city")
    updatedDf.toJSON.collect() should contain inOrderOnly(
      """{"cities":["London","Paris","London"],"upper_city":["LONDON","PARIS","LONDON"]}""",
      """{"cities":["Berlin","Barcelona","Barcelona"],"upper_city":["BERLIN","BARCELONA","BARCELONA"]}"""
    )
  }
}

private object UpperCaseUdf extends Serializable {
  def apply(name: Column): Column =
    udf((name: String) => name.toUpperCase)
      .apply(name)
}