package dataframe.action

import factory.Factory
import org.apache.spark.sql.Row
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class CollectToMapAction extends AnyFlatSpec with Matchers {
  it should "collect a DataFrame to a Map" in {
    val df = Factory.createDf("title STRING,amount INT",
      Row("Book", 10), Row("Car", 20), Row("House", 30), Row("Book", 40))
    val map = df.collect().map(row => (row.getString(0), row.getInt(1))).toMap
    map should contain only("Book" -> 40, "Car" -> 20, "House" -> 30)
  }
}