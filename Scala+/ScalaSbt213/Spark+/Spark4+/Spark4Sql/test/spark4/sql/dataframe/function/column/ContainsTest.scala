package spark4.sql.dataframe.function.column

import org.apache.spark.sql.functions.col
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark4.sql.Factory

class ContainsTest extends AnyFlatSpec with Matchers {

  it should "does a string column contain a sub-string" in {
    val df = Factory.peopleDf.filter(col("name").contains("r"))
    df.toJSON.collect should contain inOrderOnly(
      """{"name":"Peter","age":35,"gender":"M"}""",
      """{"name":"Mary","age":20,"gender":"F"}"""
    )
  }

  it should "does NOT a string column contain a sub-string" in {
    val df = Factory.peopleDf.filter(!col("name").contains("r"))
    df.toJSON.collect should contain only """{"name":"John","age":25,"gender":"M"}"""
  }

}