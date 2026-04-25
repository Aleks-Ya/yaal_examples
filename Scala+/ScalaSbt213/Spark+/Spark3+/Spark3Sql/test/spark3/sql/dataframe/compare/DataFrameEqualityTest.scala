package spark3.sql.dataframe.compare

import org.scalatest.flatspec.AnyFlatSpec
import spark3.sql.{Factory, SparkMatchers}

class DataFrameEqualityTest extends AnyFlatSpec with SparkMatchers {
  it should "verify equality of two DataFrames" in {
    val actDf = Factory.createPeopleDf()
    val expDf = Factory.createPeopleDf()
    actDf.schema shouldEqual expDf.schema
    actDf.collect() shouldEqual expDf.collect()
  }
}