package spark3.sql.dataframe.compare

import org.scalatest.flatspec.AnyFlatSpec
import spark3.sql.{Factory, SparkMatchers}

/**
 * Assert DataFrame content in unit tests.
 */
class AssertDataFrameEntireTest extends AnyFlatSpec with SparkMatchers {
  it should "assert a DataFrame content" in {
    val df = Factory.peopleDf
    df shouldContain(
      """{"name":"John","age":25,"gender":"M"}""",
      """{"name":"Peter","age":35,"gender":"M"}""",
      """{"name":"Mary","age":20,"gender":"F"}""")
  }
}