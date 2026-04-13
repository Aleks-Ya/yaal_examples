package spark3.sql.dataframe.operation.action

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark3.sql.Factory

class TailActionTest extends AnyFlatSpec with Matchers {
  it should "get the last 2 elements of a DataFrame" in {
    val array = Factory.peopleDf.sort("age").tail(2)
    array.map(_.json) should contain inOrderOnly(
      """{"name":"John","age":25,"gender":"M"}""",
      """{"name":"Peter","age":35,"gender":"M"}""")
  }
}