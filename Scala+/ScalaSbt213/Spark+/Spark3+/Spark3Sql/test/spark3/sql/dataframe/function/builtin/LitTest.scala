package spark3.sql.dataframe.function.builtin

import org.apache.spark.sql.functions.{array, col, lit}
import org.scalatest.flatspec.AnyFlatSpec
import spark3.sql.{Factory, SparkMatchers}

class LitTest extends AnyFlatSpec with SparkMatchers {
  it should "create a string literal" in {
    val df = Factory.cityListDf
    val updatedDf = df.select(
      col("city"),
      lit("Open") as "status"
    )
    updatedDf shouldContain(
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
    updatedDf shouldContain(
      """{"city":"Moscow","statuses":["Open","Closed"]}""",
      """{"city":"SPb","statuses":["Open","Closed"]}"""
    )
  }
}