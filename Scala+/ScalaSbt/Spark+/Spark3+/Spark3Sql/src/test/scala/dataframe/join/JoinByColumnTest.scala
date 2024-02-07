package dataframe.join

import factory.Factory
import org.apache.spark.sql.functions.monotonically_increasing_id
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class JoinByColumnTest extends AnyFlatSpec with Matchers {
  it should "join by a column (USING SQL operation)" in {
    val column = "id"
    val peopleDf = Factory.peopleDf.withColumn(column, monotonically_increasing_id())
    val citiesDf = Factory.cityListDf.withColumn(column, monotonically_increasing_id())
    val joinedDf = peopleDf.join(citiesDf, column)
    joinedDf.schema.simpleString shouldEqual "struct<id:bigint,name:string,age:int,gender:string,city:string>"
    joinedDf.toJSON.collect() should contain inOrderOnly(
      """{"id":0,"name":"John","age":25,"gender":"M","city":"Moscow"}""",
      """{"id":1,"name":"Peter","age":35,"gender":"M","city":"SPb"}"""
    )
  }
}