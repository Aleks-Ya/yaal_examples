package spark3.sql.dataframe.compare

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark3.sql.Factory

class DataFrameEqualityTest extends AnyFlatSpec with Matchers {
  it should "verify equality of two DataFrames" in {
    val actDf = Factory.createPeopleDf()
    val expDf = Factory.createPeopleDf()
    actDf.schema shouldEqual expDf.schema
    actDf.collect() shouldEqual expDf.collect()
  }
}