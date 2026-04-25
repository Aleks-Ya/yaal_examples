package spark3.sql.dataframe.operation.transformation

import org.scalatest.flatspec.AnyFlatSpec
import spark3.sql.{Factory, SparkMatchers}


class LimitTransformationTest extends AnyFlatSpec with SparkMatchers {

  it should "take 2 rows" in {
    val df = Factory.peopleDf
    val updatedDf = df.limit(2)
    updatedDf shouldContain(
      """{"name":"John","age":25,"gender":"M"}""",
      """{"name":"Peter","age":35,"gender":"M"}"""
    )
  }

}