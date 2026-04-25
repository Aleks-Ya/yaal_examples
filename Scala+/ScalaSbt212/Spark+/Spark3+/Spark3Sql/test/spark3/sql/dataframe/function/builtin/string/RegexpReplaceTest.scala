package spark3.sql.dataframe.function.builtin.string

import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{col, regexp_replace}
import org.apache.spark.sql.types.StringType
import org.scalatest.flatspec.AnyFlatSpec
import spark3.sql.{Factory, SparkMatchers}

class RegexpReplaceTest extends AnyFlatSpec with SparkMatchers {
  it should "use regexp_replace function" in {
    val df = Factory.createDf(Map("text" -> StringType),
      Row("A big dog eats meat"),
      Row("Mr. Big doesn't eat raw meat"),
      Row("Mr. BIG eats meat"))
    val regex1 = "[Bb]ig"
    val regex2 = "\\s(eats?) (raw )?meat"
    val updatedDf = df.select(
      col("text"),
      regexp_replace(col("text"), regex1, "Small").as("re10"),
      regexp_replace(col("text"), regex2, " like").as("re21"),
    )
    updatedDf shouldContain(
      """{"text":"A big dog eats meat","re10":"A Small dog eats meat","re21":"A big dog like"}""",
      """{"text":"Mr. Big doesn't eat raw meat","re10":"Mr. Small doesn't eat raw meat","re21":"Mr. Big doesn't like"}""",
      """{"text":"Mr. BIG eats meat","re10":"Mr. BIG eats meat","re21":"Mr. BIG like"}"""
    )
  }
}