package dataframe.function.builtin

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{col, upper}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class UpperTest extends AnyFlatSpec with Matchers {
  it should "use upper function" in {
    val df = Factory.createDf("country STRING", Row("England"), Row("Germany"), Row(null))
    val updatedDf = df.select(
      col("country"),
      upper(col("country")) as "upper")
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"country":"England","upper":"ENGLAND"}""",
      """{"country":"Germany","upper":"GERMANY"}""",
      """{"country":null,"upper":null}""")
  }
}