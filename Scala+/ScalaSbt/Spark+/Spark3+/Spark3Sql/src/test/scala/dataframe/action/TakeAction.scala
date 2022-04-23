package dataframe.action

import factory.Factory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class TakeAction extends AnyFlatSpec with Matchers {

  it should "take 2nd element of a DataFrame" in {
    val first2 = Factory.peopleDf.sort("age").take(2)
    val second = first2(1)
    second.json shouldBe """{"name":"John","age":25,"gender":"M"}"""
  }

}