package dataframe.action

import factory.Factory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class CollectActionTest extends AnyFlatSpec with Matchers {
  it should "collect a DataFrame" in {
    val array = Factory.peopleDf.sort("age").collect()
    array.map(_.json) should contain inOrderOnly(
      """{"name":"Mary","age":20,"gender":"F"}""",
      """{"name":"John","age":25,"gender":"M"}""",
      """{"name":"Peter","age":35,"gender":"M"}"""
    )
  }
}