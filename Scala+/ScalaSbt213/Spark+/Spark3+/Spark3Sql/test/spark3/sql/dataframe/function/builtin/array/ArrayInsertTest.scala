package spark3.sql.dataframe.function.builtin.array

import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{array_insert, col, lit}
import org.apache.spark.sql.types.{ArrayType, StringType}
import org.scalatest.flatspec.AnyFlatSpec
import spark3.sql.{Factory, SparkMatchers}

class ArrayInsertTest extends AnyFlatSpec with SparkMatchers {
  it should "insert element in end of array" in {
    val countryCol = "country"
    val capitalCol = "capital"
    val citiesCol = "cities"
    val df = Factory.createDf(
      Map(countryCol -> StringType, capitalCol -> StringType, citiesCol -> ArrayType(StringType)),
      Row("England", "London", Seq("Manchester", "London")),
      Row("USA", "Washington", Seq("Chicago", "Washington")))
    val updatedDf = df.select(
      col(countryCol),
      array_insert(col(citiesCol), lit(-1), col(capitalCol)) as "all_cities")
    updatedDf shouldContain(
      """{"country":"England","all_cities":["Manchester","London","London"]}""",
      """{"country":"USA","all_cities":["Chicago","Washington","Washington"]}"""
    )
  }

  it should "insert element in beginning of array" in {
    val countryCol = "country"
    val capitalCol = "capital"
    val citiesCol = "cities"
    val df = Factory.createDf(
      Map(countryCol -> StringType, capitalCol -> StringType, citiesCol -> ArrayType(StringType)),
      Row("England", "London", Seq("Manchester", "London")),
      Row("USA", "Washington", Seq("Chicago", "Washington")))
    val updatedDf = df.select(
      col(countryCol),
      array_insert(col(citiesCol), lit(1), col(capitalCol)) as "all_cities")
    updatedDf shouldContain(
      """{"country":"England","all_cities":["London","Manchester","London"]}""",
      """{"country":"USA","all_cities":["Washington","Chicago","Washington"]}"""
    )
  }
}