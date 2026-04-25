package spark3.sql.dataframe.function.builtin.string

import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{col, upper}
import org.scalatest.flatspec.AnyFlatSpec
import spark3.sql.{Factory, SparkMatchers}

class UpperTest extends AnyFlatSpec with SparkMatchers {
  it should "use upper function" in {
    val df = Factory.createDf("country STRING", Row("England"), Row("Germany"), Row(null))
    val updatedDf = df.select(
      col("country"),
      upper(col("country")) as "upper")
    updatedDf shouldContain(
      """{"country":"England","upper":"ENGLAND"}""",
      """{"country":"Germany","upper":"GERMANY"}""",
      """{"country":null,"upper":null}""")
  }
}