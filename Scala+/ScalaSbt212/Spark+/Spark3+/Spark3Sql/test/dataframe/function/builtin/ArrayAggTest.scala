package dataframe.function.builtin

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{array_agg, col}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ArrayAggTest extends AnyFlatSpec with Matchers {

  it should "aggregate a group to an array" in {
    val df = Factory.createDf("id STRING, count INT",
      Row("1", 100),
      Row("2", 10),
      Row("1", 200),
      Row("2", 20))
    val updatedDf = df.groupBy("id").agg(array_agg(col("count")).as("all"))
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"id":"1","all":[100,200]}""",
      """{"id":"2","all":[10,20]}""")
  }

}