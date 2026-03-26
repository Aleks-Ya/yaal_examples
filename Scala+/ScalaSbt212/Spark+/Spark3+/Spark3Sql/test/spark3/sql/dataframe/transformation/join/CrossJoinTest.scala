package spark3.sql.dataframe.transformation.join

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark3.sql.Factory


class CrossJoinTest extends AnyFlatSpec with Matchers {
  it should "do a cross-join" in {
    val peopleDf = Factory.peopleDf
    val citiesDf = Factory.cityListDf
    val joinedDf = peopleDf.crossJoin(citiesDf)
    joinedDf.schema.simpleString shouldEqual "struct<name:string,age:int,gender:string,city:string>"
    joinedDf.toJSON.collect should contain inOrderOnly(
      """{"name":"John","age":25,"gender":"M","city":"Moscow"}""",
      """{"name":"John","age":25,"gender":"M","city":"SPb"}""",
      """{"name":"Peter","age":35,"gender":"M","city":"Moscow"}""",
      """{"name":"Peter","age":35,"gender":"M","city":"SPb"}""",
      """{"name":"Mary","age":20,"gender":"F","city":"Moscow"}""",
      """{"name":"Mary","age":20,"gender":"F","city":"SPb"}"""
    )
  }
}