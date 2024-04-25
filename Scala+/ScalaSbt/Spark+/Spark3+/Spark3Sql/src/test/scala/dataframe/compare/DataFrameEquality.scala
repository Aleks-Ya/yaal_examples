package dataframe.compare

import factory.Factory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class DataFrameEquality extends AnyFlatSpec with Matchers {
  it should "verify equality of two DataFrames" in {
    val actDf = Factory.createPeopleDf()
    val expDf = Factory.createPeopleDf()
    actDf.schema shouldEqual expDf.schema
    actDf.collect() shouldEqual expDf.collect()
  }
}