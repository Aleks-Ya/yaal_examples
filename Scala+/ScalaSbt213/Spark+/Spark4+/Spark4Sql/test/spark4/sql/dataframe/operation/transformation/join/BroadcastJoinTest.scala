package spark4.sql.dataframe.operation.transformation.join

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.broadcast
import org.scalatest.flatspec.AnyFlatSpec
import spark4.sql.{Factory, SparkMatchers}

class BroadcastJoinTest extends AnyFlatSpec with SparkMatchers {

  it should "perform a broadcast join" in {
    val smallDf: DataFrame = Factory.peopleDf
    smallDf shouldHaveDDL "name STRING,age INT,gender STRING"
    val bigDf: DataFrame = Factory.cityListDf
    bigDf shouldHaveDDL "city STRING"

    val joinedDf: DataFrame = bigDf.join(broadcast(smallDf))
    joinedDf shouldHaveDDL "city STRING,name STRING,age INT,gender STRING"
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