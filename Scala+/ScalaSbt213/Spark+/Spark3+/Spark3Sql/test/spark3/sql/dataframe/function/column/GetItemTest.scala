package spark3.sql.dataframe.function.column

import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.col
import org.scalatest.flatspec.AnyFlatSpec
import spark3.sql.{Factory, SparkMatchers}

class GetItemTest extends AnyFlatSpec with SparkMatchers {

  it should "get 1st element of array" in {
    val df = Factory.createDf("name STRING, orders ARRAY<INT>",
      Row("USA", Array(10, 20)),
      Row("Canada", Array()))
    val updatedDf = df.withColumn("first_order", col("orders").getItem(0))
    updatedDf shouldHaveDDL "name STRING,orders ARRAY<INT>,first_order INT"
    updatedDf shouldContain(
      """{"name":"USA","orders":[10,20],"first_order":10}""",
      """{"name":"Canada","orders":[],"first_order":null}""")
  }

}