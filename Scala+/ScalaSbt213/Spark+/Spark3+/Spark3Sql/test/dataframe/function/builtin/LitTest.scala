package dataframe.function.builtin

import factory.Factory
import org.apache.spark.sql.functions.{array, col, lit}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class LitTest extends AnyFlatSpec with Matchers {
  it should "create a string literal" in {
    val df = Factory.cityListDf
    val updatedDf = df.select(
      col("city"),
      lit("Open") as "status"
    )
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"city":"Moscow","status":"Open"}""",
      """{"city":"SPb","status":"Open"}"""
    )
  }

  it should "create an array literal" in {
    val statusesSeq = Seq("Open", "Closed")
    val df = Factory.cityListDf
    val updatedDf = df.select(
      col("city"),
      array(statusesSeq.map(lit): _*) as "statuses")
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"city":"Moscow","statuses":["Open","Closed"]}""",
      """{"city":"SPb","statuses":["Open","Closed"]}"""
    )
  }
}