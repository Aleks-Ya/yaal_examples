package dataframe.function

import factory.Factory
import org.apache.spark.sql.functions.{col, upper}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class UpperFunction extends AnyFlatSpec with Matchers {
  it should "use upper function" in {
    val df = Factory.cityListDf
    val updatedDf = df.select(
      col("city"),
      upper(col("city")) as "upper"
    )
    updatedDf.toJSON.collect() should contain inOrderOnly(
      """{"city":"Moscow","upper":"MOSCOW"}""",
      """{"city":"SPb","upper":"SPB"}"""
    )
  }
}