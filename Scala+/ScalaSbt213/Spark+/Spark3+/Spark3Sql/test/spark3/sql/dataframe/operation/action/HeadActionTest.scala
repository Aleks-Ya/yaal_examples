package spark3.sql.dataframe.operation.action

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark3.sql.Factory

class HeadActionTest extends AnyFlatSpec with Matchers {
  it should "get the first 2 elements of a DataFrame" in {
    val array = Factory.peopleDf.sort("age").head(2)
    array.map(_.json) should contain inOrderOnly(
      """{"name":"Mary","age":20,"gender":"F"}""",
      """{"name":"John","age":25,"gender":"M"}""")
  }
}