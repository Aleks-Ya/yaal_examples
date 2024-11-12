package dataframe.function.builtin

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{col, split}
import org.apache.spark.sql.types.StringType
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class SplitFunction extends AnyFlatSpec with Matchers {
  it should "use split function" in {
    val df = Factory.createDf(Map("person" -> StringType),
      Row("John-30-M"),
      Row("Mary-25-F"))
    val updatedDf = df.select(
      col("person"),
      split(col("person"), "-") as "split")
    updatedDf.toJSON.collect() should contain inOrderOnly(
      """{"person":"John-30-M","split":["John","30","M"]}""",
      """{"person":"Mary-25-F","split":["Mary","25","F"]}"""
    )
  }
}