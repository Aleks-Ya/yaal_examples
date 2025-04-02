package dataframe.show

import factory.Factory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class DescribeDfTest extends AnyFlatSpec with Matchers {
  it should "describe columns" in {
    val df = Factory.peopleDf
    val describeDf = df.describe("name", "age", "gender")
    describeDf.show(false)
    describeDf.toJSON.collect() should contain inOrderOnly(
      """{"summary":"count","name":"3","age":"3","gender":"3"}""",
      """{"summary":"mean","name":null,"age":"26.666666666666668","gender":null}""",
      """{"summary":"stddev","name":null,"age":"7.637626158259733","gender":null}""",
      """{"summary":"min","name":"John","age":"20","gender":"F"}""",
      """{"summary":"max","name":"Peter","age":"35","gender":"M"}"""
    )
  }
}