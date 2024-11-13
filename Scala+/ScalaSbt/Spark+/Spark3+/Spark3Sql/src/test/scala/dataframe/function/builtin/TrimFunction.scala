package dataframe.function.builtin

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{col, trim}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class TrimFunction extends AnyFlatSpec with Matchers {
  it should "use trim function" in {
    val df = Factory.createDf("country STRING", Row("  England "), Row("Germany"))
    val updatedDf = df.select(
      col("country"),
      trim(col("country")) as "trimmed")
    updatedDf.toJSON.collect() should contain inOrderOnly(
      """{"country":"  England ","trimmed":"England"}""",
      """{"country":"Germany","trimmed":"Germany"}""")
  }
}