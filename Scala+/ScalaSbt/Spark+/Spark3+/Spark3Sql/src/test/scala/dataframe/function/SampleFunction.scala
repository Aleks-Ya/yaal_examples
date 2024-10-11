package dataframe.function

import factory.Factory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class SampleFunction extends AnyFlatSpec with Matchers {
  it should "get a sample from a DataFrame" in {
    val df = Factory.peopleDf
    val sampleDf = df.sample(0.7)
    sampleDf.count shouldEqual 2
  }
}