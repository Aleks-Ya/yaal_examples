package dataframe.transformation

import factory.Factory
import org.apache.spark.sql.functions.{asc, desc}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class SortTransformationTest extends AnyFlatSpec with Matchers {

  it should "sort a DataFrame in default order (ASC)" in {
    val df = Factory.peopleDf.sort("age")
    df.toJSON.collect should contain inOrderOnly(
      """{"name":"Mary","age":20,"gender":"F"}""",
      """{"name":"John","age":25,"gender":"M"}""",
      """{"name":"Peter","age":35,"gender":"M"}"""
    )
  }

  it should "sort a DataFrame in DESC order" in {
    val df = Factory.peopleDf.sort(desc("age"))
    df.toJSON.collect should contain inOrderOnly(
      """{"name":"Peter","age":35,"gender":"M"}""",
      """{"name":"John","age":25,"gender":"M"}""",
      """{"name":"Mary","age":20,"gender":"F"}"""
    )
  }

  it should "sort a DataFrame in ASC order (default)" in {
    val df = Factory.peopleDf.sort(asc("age"))
    df.toJSON.collect should contain inOrderOnly(
      """{"name":"Mary","age":20,"gender":"F"}""",
      """{"name":"John","age":25,"gender":"M"}""",
      """{"name":"Peter","age":35,"gender":"M"}"""
    )
  }

  it should "sort a DataFrame by several columns" in {
    val df = Factory.peopleDf.sort(asc("gender"), asc("name"))
    df.toJSON.collect should contain inOrderOnly(
      """{"name":"Mary","age":20,"gender":"F"}""",
      """{"name":"John","age":25,"gender":"M"}""",
      """{"name":"Peter","age":35,"gender":"M"}"""
    )
  }
}