package dataframe.function.builtin

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{col, sort_array}
import org.apache.spark.sql.types.{ArrayType, StringType}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class SortArrayFunction extends AnyFlatSpec with Matchers {
  it should "sort an array column" in {
    val df = Factory.createDf(Map("person" -> StringType, "countries" -> ArrayType(StringType)),
      Row("John", Seq("USA", "Germany", "UK")),
      Row("Mary", Seq("Belgium", "Canada", "Australia")))
    df.toJSON.collect should contain inOrderOnly(
      """{"person":"John","countries":["USA","Germany","UK"]}""",
      """{"person":"Mary","countries":["Belgium","Canada","Australia"]}"""
    )

    val updatedDf = df.select(
      col("person"),
      sort_array(col("countries")).as("countries_asc"),
      sort_array(col("countries"), asc = false).as("countries_desc")
    )
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"person":"John","countries_asc":["Germany","UK","USA"],"countries_desc":["USA","UK","Germany"]}""",
      """{"person":"Mary","countries_asc":["Australia","Belgium","Canada"],"countries_desc":["Canada","Belgium","Australia"]}"""
    )
  }
}