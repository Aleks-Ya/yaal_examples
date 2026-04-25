package spark4.sql.dataframe.function.builtin.array

import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{array_agg, col}
import org.scalatest.flatspec.AnyFlatSpec
import spark4.sql.{Factory, SparkMatchers}

class ArrayAggTest extends AnyFlatSpec with SparkMatchers {

  it should "aggregate a group to an array" in {
    val df = Factory.createDf("id STRING, count INT",
      Row("1", 100),
      Row("2", 10),
      Row("1", 200),
      Row("2", 20))
    val updatedDf = df.groupBy("id").agg(array_agg(col("count")).as("all"))
    updatedDf shouldContain(
      """{"id":"1","all":[100,200]}""",
      """{"id":"2","all":[10,20]}""")
  }

}