package dataframe.action

import factory.Factory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class TakeAction extends AnyFlatSpec with Matchers {
  it should "take 2nd element of a DataFrame" in {
    val first2 = Factory.peopleDf.sort("age").take(2)
    first2.map(_.json) should contain inOrderOnly(
      """{"name":"Mary","age":20,"gender":"F"}""",
      """{"name":"John","age":25,"gender":"M"}""")
  }
}