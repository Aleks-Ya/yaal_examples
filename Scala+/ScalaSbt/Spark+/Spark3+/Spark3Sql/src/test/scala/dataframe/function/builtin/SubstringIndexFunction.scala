package dataframe.function.builtin

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{col, substring_index}
import org.apache.spark.sql.types.StringType
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class SubstringIndexFunction extends AnyFlatSpec with Matchers {
  it should "use filter function" in {
    val df = Factory.createDf(Map("text" -> StringType),
      Row("a,b,c"),
      Row("e,f,g"))
    val updatedDf = df.select(
      substring_index(col("text"), ",", 2) as "index")
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"index":"a,b"}""",
      """{"index":"e,f"}"""
    )
  }
}