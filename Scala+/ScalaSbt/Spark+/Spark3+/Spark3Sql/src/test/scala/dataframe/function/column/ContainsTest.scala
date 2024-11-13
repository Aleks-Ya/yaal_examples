package dataframe.function.column

import factory.Factory
import org.apache.spark.sql.functions.col
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ContainsTest extends AnyFlatSpec with Matchers {
  it should "does a string column contain a sub-string" in {
    val df = Factory.peopleDf.filter(col("name").contains("r"))
    df.toJSON.collect() should contain inOrderOnly(
      """{"name":"Peter","age":35,"gender":"M"}""",
      """{"name":"Mary","age":20,"gender":"F"}"""
    )
  }
}