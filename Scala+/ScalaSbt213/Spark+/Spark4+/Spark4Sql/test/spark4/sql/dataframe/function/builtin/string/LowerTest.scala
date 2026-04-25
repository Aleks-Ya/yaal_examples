package spark4.sql.dataframe.function.builtin.string

import org.apache.spark.sql.functions.{col, lower}
import org.scalatest.flatspec.AnyFlatSpec
import spark4.sql.{Factory, SparkMatchers}

class LowerTest extends AnyFlatSpec with SparkMatchers {
  it should "use lower function" in {
    val df = Factory.cityListDf
    val updatedDf = df.select(
      col("city"),
      lower(col("city")) as "lower"
    )
    updatedDf shouldContain(
      """{"city":"Moscow","lower":"moscow"}""",
      """{"city":"SPb","lower":"spb"}"""
    )
  }
}