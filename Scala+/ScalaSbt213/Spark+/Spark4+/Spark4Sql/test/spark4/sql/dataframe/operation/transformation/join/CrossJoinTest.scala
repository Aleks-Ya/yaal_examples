package spark4.sql.dataframe.operation.transformation.join

import org.apache.spark.sql.DataFrame
import org.scalatest.flatspec.AnyFlatSpec
import spark4.sql.{Factory, SparkMatchers}

class CrossJoinTest extends AnyFlatSpec with SparkMatchers {
  it should "do a cross-join" in {
    val peopleDf = Factory.peopleDf
    peopleDf shouldHaveDDL "name STRING,age INT,gender STRING"
    val citiesDf = Factory.cityListDf
    citiesDf shouldHaveDDL "city STRING"

    val joinedDf: DataFrame = peopleDf.crossJoin(citiesDf)
    joinedDf shouldHaveDDL "name STRING,age INT,gender STRING,city STRING"
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