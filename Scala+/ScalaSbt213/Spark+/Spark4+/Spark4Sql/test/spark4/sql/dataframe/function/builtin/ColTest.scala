package spark4.sql.dataframe.function.builtin

import org.apache.spark.sql.functions.col
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark4.sql.Factory

class ColTest extends AnyFlatSpec with Matchers {

  it should "select a column" in {
    val df = Factory.peopleDf
    val updatedDf = df.select(col("name"), col("gender"))
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"name":"John","gender":"M"}""",
      """{"name":"Peter","gender":"M"}""",
      """{"name":"Mary","gender":"F"}""")
  }

}