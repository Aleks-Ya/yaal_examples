package dataframe.predicate

import factory.Factory
import org.apache.spark.sql.functions.col
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class GreaterThanTest extends AnyFlatSpec with Matchers {

  it should "use greater than symbol" in {
    val df = Factory.peopleDf
    val updatedDf = df.filter(col("age") > 20)
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"name":"John","age":25,"gender":"M"}""",
      """{"name":"Peter","age":35,"gender":"M"}""")
  }

  it should "use greater than method" in {
    val df = Factory.peopleDf
    val updatedDf = df.filter(col("age").gt(20))
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"name":"John","age":25,"gender":"M"}""",
      """{"name":"Peter","age":35,"gender":"M"}""")
  }

}