package spark3.sql.dataframe.function.builtin.string

import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{col, trim}
import org.scalatest.flatspec.AnyFlatSpec
import spark3.sql.{Factory, SparkMatchers}

class TrimTest extends AnyFlatSpec with SparkMatchers {
  it should "use trim function" in {
    val df = Factory.createDf("country STRING",
      Row("  England "),
      Row("Germany"),
      Row("  "),
      Row(null))
    val updatedDf = df.select(
      col("country"),
      trim(col("country")) as "trimmed")
    updatedDf shouldHaveDDL "country STRING,trimmed STRING"
    updatedDf shouldContain(
      """{"country":"  England ","trimmed":"England"}""",
      """{"country":"Germany","trimmed":"Germany"}""",
      """{"country":"  ","trimmed":""}""",
      """{"country":null,"trimmed":null}"""
    )
  }
}