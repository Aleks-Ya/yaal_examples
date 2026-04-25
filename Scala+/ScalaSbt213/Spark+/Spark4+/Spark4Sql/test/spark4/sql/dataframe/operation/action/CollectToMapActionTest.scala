package spark4.sql.dataframe.operation.action

import org.apache.spark.sql.Row
import org.scalatest.flatspec.AnyFlatSpec
import spark4.sql.{Factory, SparkMatchers}

class CollectToMapActionTest extends AnyFlatSpec with SparkMatchers {
  it should "collect a DataFrame to a Map" in {
    val df = Factory.createDf("title STRING,amount INT",
      Row("Book", 10), Row("Car", 20), Row("House", 30), Row("Book", 40))
    val map = df.collect().map(row => (row.getString(0), row.getInt(1))).toMap
    map should contain only("Book" -> 40, "Car" -> 20, "House" -> 30)
  }
}