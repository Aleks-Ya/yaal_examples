package spark4.sql.dataframe.function.column

import org.apache.spark.sql.functions.col
import org.scalatest.flatspec.AnyFlatSpec
import spark4.sql.{Factory, SparkMatchers}

class ContainsTest extends AnyFlatSpec with SparkMatchers {

  it should "filter a string column that contains given sub-string" in {
    val df = Factory.peopleDf.filter(col("name").contains("r"))
    df shouldHaveDDL "name STRING,age INT,gender STRING"
    df shouldContain(
      """{"name":"Peter","age":35,"gender":"M"}""",
      """{"name":"Mary","age":20,"gender":"F"}"""
    )
  }

  it should "filter a string column that does NOT contain given sub-string" in {
    val df = Factory.peopleDf.filter(!col("name").contains("r"))
    df shouldHaveDDL "name STRING,age INT,gender STRING"
    df shouldContain """{"name":"John","age":25,"gender":"M"}"""
  }

}