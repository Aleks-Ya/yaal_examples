package spark4.sql.dataframe.function.builtin

import org.apache.spark.sql.functions.{array, col, lit}
import org.scalatest.flatspec.AnyFlatSpec
import spark4.sql.{Factory, SparkMatchers}

class LitTest extends AnyFlatSpec with SparkMatchers {

  it should "create a string literal" in {
    val df = Factory.cityListDf
    val updatedDf = df.select(
      col("city"),
      lit("Open") as "status"
    )
    updatedDf shouldHaveDDL "city STRING,status STRING NOT NULL"
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
    updatedDf shouldHaveDDL "city STRING,statuses ARRAY<STRING> NOT NULL"
    updatedDf shouldContain(
      """{"city":"Moscow","statuses":["Open","Closed"]}""",
      """{"city":"SPb","statuses":["Open","Closed"]}"""
    )
  }

}