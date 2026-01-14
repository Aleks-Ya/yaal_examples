package dataframe.function.builtin

import factory.Factory
import org.apache.spark.sql.functions.col
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

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