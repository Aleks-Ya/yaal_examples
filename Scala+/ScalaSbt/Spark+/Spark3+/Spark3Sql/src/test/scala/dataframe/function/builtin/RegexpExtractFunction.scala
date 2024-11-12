package dataframe.function.builtin

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{col, regexp_extract}
import org.apache.spark.sql.types.StringType
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class RegexpExtractFunction extends AnyFlatSpec with Matchers {
  it should "use regexp_extract function" in {
    val df = Factory.createDf(Map("text" -> StringType),
      Row("A big dog eats meat"),
      Row("Mr. Big doesn't eat raw meat"),
      Row("Mr. BIG eats meat"))
    val regex1 = "[Bb]ig"
    val regex2 = "\\s(eats?) (raw )?meat"
    val updatedDf = df.select(
      col("text"),
      regexp_extract(col("text"), regex1, 0).as("re10"),
      regexp_extract(col("text"), regex2, 0).as("re20"),
      regexp_extract(col("text"), regex2, 1).as("re21"),
      regexp_extract(col("text"), regex2, 2).as("re22")
    )
    updatedDf.toJSON.collect() should contain inOrderOnly(
      """{"text":"A big dog eats meat","re10":"big","re20":" eats meat","re21":"eats","re22":""}""",
      """{"text":"Mr. Big doesn't eat raw meat","re10":"Big","re20":" eat raw meat","re21":"eat","re22":"raw "}""",
      """{"text":"Mr. BIG eats meat","re10":"","re20":" eats meat","re21":"eats","re22":""}"""
    )
  }
}