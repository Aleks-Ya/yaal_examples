package spark4.sql.dataframe.function.builtin

import org.apache.spark.sql.functions.col
import org.scalatest.flatspec.AnyFlatSpec
import spark4.sql.{Factory, SparkMatchers}

class ColTest extends AnyFlatSpec with SparkMatchers {

  it should "select a column" in {
    val df = Factory.peopleDf
    val updatedDf = df.select(col("name"), col("gender"))
    updatedDf shouldContain(
      """{"name":"John","gender":"M"}""",
      """{"name":"Peter","gender":"M"}""",
      """{"name":"Mary","gender":"F"}""")
  }

}