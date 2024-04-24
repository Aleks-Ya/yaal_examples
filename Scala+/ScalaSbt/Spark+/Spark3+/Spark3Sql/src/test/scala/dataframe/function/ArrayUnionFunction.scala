package dataframe.function

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{array_union, col}
import org.apache.spark.sql.types.{ArrayType, StringType}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ArrayUnionFunction extends AnyFlatSpec with Matchers {
  it should "use array_union function" in {
    val countryCol = "country"
    val bigCitiesCol = "big_cities"
    val smallCitiesCol = "small_cities"
    val df = Factory.createDf(
      Map(countryCol -> StringType, bigCitiesCol -> ArrayType(StringType), smallCitiesCol -> ArrayType(StringType)),
      Row("England", Seq("London", "Manchester", "London"), Seq("London", "Birmingham")),
      Row("USA", Seq("Chicago"), Seq("Houston", "Phoenix")))
    val updatedDf = df.select(
      col(countryCol),
      array_union(col(bigCitiesCol), col(smallCitiesCol)) as "all_cities")
    updatedDf.toJSON.collect() should contain inOrderOnly(
      """{"country":"England","all_cities":["London","Manchester","Birmingham"]}""",
      """{"country":"USA","all_cities":["Chicago","Houston","Phoenix"]}"""
    )
  }
}