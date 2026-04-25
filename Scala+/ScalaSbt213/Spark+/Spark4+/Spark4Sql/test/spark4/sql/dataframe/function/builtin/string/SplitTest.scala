package spark4.sql.dataframe.function.builtin.string

import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{col, split}
import org.scalatest.flatspec.AnyFlatSpec
import spark4.sql.{Factory, SparkMatchers}

class SplitTest extends AnyFlatSpec with SparkMatchers {
  it should "use split function" in {
    val df = Factory.createDf("person STRING",
      Row("John-30-M"),
      Row("Mary-25-F"))
    val updatedDf = df.select(
      col("person"),
      split(col("person"), "-") as "split")
    updatedDf shouldHaveDDL "person STRING,split ARRAY<STRING>"
    updatedDf shouldContain(
      """{"person":"John-30-M","split":["John","30","M"]}""",
      """{"person":"Mary-25-F","split":["Mary","25","F"]}"""
    )
  }
}