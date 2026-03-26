package spark3.sql.dataframe.compare

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark3.sql.Factory

/**
 * Assert DataFrame content in unit tests.
 */
class AssertDataFrameEntireTest extends AnyFlatSpec with Matchers {
  it should "assert a DataFrame content" in {
    val df = Factory.peopleDf
    df.toJSON.collect should contain inOrderOnly(
      """{"name":"John","age":25,"gender":"M"}""",
      """{"name":"Peter","age":35,"gender":"M"}""",
      """{"name":"Mary","age":20,"gender":"F"}""")
  }
}