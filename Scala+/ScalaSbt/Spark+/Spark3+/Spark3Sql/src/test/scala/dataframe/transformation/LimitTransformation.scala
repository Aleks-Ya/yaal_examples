package dataframe.transformation

import factory.Factory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class LimitTransformation extends AnyFlatSpec with Matchers {

  it should "take 2 rows" in {
    val df = Factory.peopleDf
    val updatedDf = df.limit(2)
    updatedDf.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","age":25,"gender":"M"}""",
      """{"name":"Peter","age":35,"gender":"M"}"""
    )
  }

}