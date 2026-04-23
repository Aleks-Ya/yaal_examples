package spark4.sql.dataframe.function.builtin.string

import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{col, split}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark4.sql.Factory

class SplitTest extends AnyFlatSpec with Matchers {
  it should "use split function" in {
    val df = Factory.createDf("person STRING",
      Row("John-30-M"),
      Row("Mary-25-F"))
    val updatedDf = df.select(
      col("person"),
      split(col("person"), "-") as "split")
    updatedDf.schema.toDDL shouldEqual "person STRING,split ARRAY<STRING>"
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"person":"John-30-M","split":["John","30","M"]}""",
      """{"person":"Mary-25-F","split":["Mary","25","F"]}"""
    )
  }
}