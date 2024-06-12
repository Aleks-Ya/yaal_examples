package dataframe.function

import factory.Factory
import org.apache.spark.sql.functions.{col, lit}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class LitFunction extends AnyFlatSpec with Matchers {
  it should "use lit function" in {
    val df = Factory.cityListDf
    val updatedDf = df.select(
      col("city"),
      lit("Open") as "status"
    )
    updatedDf.toJSON.collect() should contain inOrderOnly(
      """{"city":"Moscow","status":"Open"}""",
      """{"city":"SPb","status":"Open"}"""
    )
  }
}