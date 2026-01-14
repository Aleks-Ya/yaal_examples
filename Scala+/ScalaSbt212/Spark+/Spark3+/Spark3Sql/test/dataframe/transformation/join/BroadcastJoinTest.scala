package dataframe.transformation.join

import factory.Factory
import org.apache.spark.sql.functions.broadcast
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class BroadcastJoinTest extends AnyFlatSpec with Matchers {

  it should "performa a broadcast join" in {
    val smallDf = Factory.peopleDf
    val bigDf = Factory.cityListDf
    val joinedDf = bigDf.join(broadcast(smallDf))
    joinedDf.schema.simpleString shouldEqual "struct<city:string,name:string,age:int,gender:string>"
    joinedDf.toJSON.collect should contain inOrderOnly(
      """{"city":"Moscow","name":"John","age":25,"gender":"M"}""",
      """{"city":"Moscow","name":"Peter","age":35,"gender":"M"}""",
      """{"city":"Moscow","name":"Mary","age":20,"gender":"F"}""",
      """{"city":"SPb","name":"John","age":25,"gender":"M"}""",
      """{"city":"SPb","name":"Peter","age":35,"gender":"M"}""",
      """{"city":"SPb","name":"Mary","age":20,"gender":"F"}"""
    )
  }
}