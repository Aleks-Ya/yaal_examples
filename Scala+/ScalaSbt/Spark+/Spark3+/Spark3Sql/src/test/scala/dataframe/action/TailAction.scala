package dataframe.action

import factory.Factory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class TailAction extends AnyFlatSpec with Matchers {

  it should "get the last 2 elements of a DataFrame" in {
    val last2 = Factory.peopleDf.sort("age").tail(2)
    last2.map(_.json) should contain inOrderOnly(
      """{"name":"John","age":25,"gender":"M"}""",
      """{"name":"Peter","age":35,"gender":"M"}"""
    )
  }

}