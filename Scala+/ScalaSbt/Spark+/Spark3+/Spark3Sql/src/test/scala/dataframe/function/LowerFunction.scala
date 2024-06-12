package dataframe.function

import factory.Factory
import org.apache.spark.sql.functions.{col, lower}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class LowerFunction extends AnyFlatSpec with Matchers {
  it should "use lower function" in {
    val df = Factory.cityListDf
    val updatedDf = df.select(
      col("city"),
      lower(col("city")) as "lower"
    )
    updatedDf.toJSON.collect() should contain inOrderOnly(
      """{"city":"Moscow","lower":"moscow"}""",
      """{"city":"SPb","lower":"spb"}"""
    )
  }
}