package spark3.sql.dataframe.operation.action

import org.scalatest.flatspec.AnyFlatSpec
import spark3.sql.{Factory, SparkMatchers}

class TakeActionTest extends AnyFlatSpec with SparkMatchers {
  it should "take 2nd element of a DataFrame" in {
    val array = Factory.peopleDf.sort("age").take(2)
    array.map(_.json) should contain inOrderOnly(
      """{"name":"Mary","age":20,"gender":"F"}""",
      """{"name":"John","age":25,"gender":"M"}""")
  }
}