package spark3.sql.dataframe.operation.transformation.join

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.broadcast
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark3.sql.Factory

class BroadcastJoinTest extends AnyFlatSpec with Matchers {

  it should "perform a broadcast join" in {
    val smallDf: DataFrame = Factory.peopleDf
    smallDf.schema.toDDL shouldEqual "name STRING,age INT,gender STRING"
    val bigDf: DataFrame = Factory.cityListDf
    bigDf.schema.toDDL shouldEqual "city STRING"

    val joinedDf: DataFrame = bigDf.join(broadcast(smallDf))
    joinedDf.schema.toDDL shouldEqual "city STRING,name STRING,age INT,gender STRING"
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