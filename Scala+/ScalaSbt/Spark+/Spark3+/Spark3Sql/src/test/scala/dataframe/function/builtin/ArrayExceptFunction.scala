package dataframe.function.builtin

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{array_except, col}
import org.apache.spark.sql.types.{ArrayType, StringType}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ArrayExceptFunction extends AnyFlatSpec with Matchers {
  it should "exclude one array from another array" in {
    val countryCol = "country"
    val bigCitiesCol = "big_cities"
    val allCitiesCol = "all_cities"
    val df = Factory.createDf(
      Map(countryCol -> StringType, bigCitiesCol -> ArrayType(StringType), allCitiesCol -> ArrayType(StringType)),
      Row("England", Seq("London", "Manchester"), Seq("London", "Birmingham", "Manchester")),
      Row("USA", Seq("Chicago", "Washington"), Seq("Houston", "Phoenix", "Chicago", "Washington")))
    val updatedDf = df.select(
      col(countryCol),
      array_except(col(allCitiesCol), col(bigCitiesCol)) as "small_cities")
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"country":"England","small_cities":["Birmingham"]}""",
      """{"country":"USA","small_cities":["Houston","Phoenix"]}"""
    )
  }
}