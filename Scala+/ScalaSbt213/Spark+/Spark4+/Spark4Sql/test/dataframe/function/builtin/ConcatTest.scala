package dataframe.function.builtin

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{col, concat}
import org.apache.spark.sql.types.{ArrayType, StringType}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ConcatTest extends AnyFlatSpec with Matchers {
  it should "join arrays" in {
    val countryCol = "country"
    val bigCitiesCol = "big_cities"
    val smallCitiesCol = "small_cities"
    val df = Factory.createDf(
      Map(countryCol -> StringType, bigCitiesCol -> ArrayType(StringType), smallCitiesCol -> ArrayType(StringType)),
      Row("England", Seq("London", "Manchester", "London"), Seq("London", "Birmingham")),
      Row("USA", Seq("Chicago"), Seq("Houston", "Phoenix")))
    val updatedDf = df.select(
      col(countryCol),
      concat(col(bigCitiesCol), col(smallCitiesCol)) as "all_cities")
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"country":"England","all_cities":["London","Manchester","London","London","Birmingham"]}""",
      """{"country":"USA","all_cities":["Chicago","Houston","Phoenix"]}"""
    )
  }
}