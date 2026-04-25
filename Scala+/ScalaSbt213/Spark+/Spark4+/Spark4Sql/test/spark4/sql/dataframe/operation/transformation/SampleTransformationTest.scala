package spark4.sql.dataframe.operation.transformation

import org.scalatest.flatspec.AnyFlatSpec
import spark4.sql.{Factory, SparkMatchers}

class SampleTransformationTest extends AnyFlatSpec with SparkMatchers {
  it should "get a sample from a DataFrame" in {
    val df = Factory.peopleDf
    val sampleDf = df.sample(0.7)
    sampleDf.count shouldEqual 2
  }
}