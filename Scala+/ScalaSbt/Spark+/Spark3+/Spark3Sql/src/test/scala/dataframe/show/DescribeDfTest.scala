package dataframe.show

import factory.Factory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class DescribeDfTest extends AnyFlatSpec with Matchers {
  it should "describe columns" in {
    val df = Factory.peopleDf
    val describeDf = df.describe("name", "age", "gender")
    describeDf.show(false)
  }
}