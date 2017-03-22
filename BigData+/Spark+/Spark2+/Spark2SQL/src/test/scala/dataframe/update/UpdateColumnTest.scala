package dataframe.update

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions._
import org.scalatest.{FlatSpec, Matchers}

class UpdateColumnTest extends FlatSpec with Matchers {

  it should "multiply column value" in {
    val df = Factory.peopleDf
    df.show
    df.withColumn("age", col("age").multiply(2)).show
  }

  it should "change" in {
    val df = Factory.peopleDf
    df.show
    implicit val mapEncoder = org.apache.spark.sql.Encoders.kryo[Row]
    df.map(row => {
      val index = row.fieldIndex("name")
      val newValue = "Mr. " + row.getString(index)
      val rowSeq = row.toSeq.toBuffer
      rowSeq(index) = newValue
      val newRow = Row(rowSeq)
      println("New row: " + newRow)
      newRow
    }).foreach(row => println(row))
  }

}