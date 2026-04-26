package spark4.sql.dataframe.function.builtin.array

import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{col, sort_array}
import org.scalatest.flatspec.AnyFlatSpec
import spark4.sql.{Factory, SparkMatchers}

class SortArrayTest extends AnyFlatSpec with SparkMatchers {
  it should "sort an array column" in {
    val df = Factory.createDf("person STRING, countries ARRAY<STRING>",
      Row("John", Seq("USA", "Germany", "UK")),
      Row("Mary", Seq("Belgium", "Canada", "Australia")))
    df shouldContain(
      """{"person":"John","countries":["USA","Germany","UK"]}""",
      """{"person":"Mary","countries":["Belgium","Canada","Australia"]}"""
    )
    val updatedDf = df.select(
      col("person"),
      sort_array(col("countries")).as("countries_asc"),
      sort_array(col("countries"), asc = false).as("countries_desc")
    )
    updatedDf shouldHaveDDL "person STRING,countries_asc ARRAY<STRING>,countries_desc ARRAY<STRING>"
    updatedDf shouldContain(
      """{"person":"John","countries_asc":["Germany","UK","USA"],"countries_desc":["USA","UK","Germany"]}""",
      """{"person":"Mary","countries_asc":["Australia","Belgium","Canada"],"countries_desc":["Canada","Belgium","Australia"]}"""
    )
  }
}