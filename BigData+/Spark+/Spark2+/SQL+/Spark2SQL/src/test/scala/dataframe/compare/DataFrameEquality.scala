package dataframe.compare

import factory.Factory
import org.scalatest.FlatSpec
import org.scalatest.Matchers._

class DataFrameEquality extends FlatSpec {

  it should "verify equality of two DataFrames" in {
    val actDf = Factory.createPeopleDf()
    val expDf = Factory.createPeopleDf()

    actDf.schema shouldEqual expDf.schema
    actDf.collect() shouldEqual expDf.collect()
  }

}