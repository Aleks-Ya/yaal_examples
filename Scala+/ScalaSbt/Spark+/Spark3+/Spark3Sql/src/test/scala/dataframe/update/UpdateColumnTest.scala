package dataframe.update

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class UpdateColumnTest extends AnyFlatSpec with Matchers {

  it should "multiply column value" in {
    val df = Factory.peopleDf
    df.show
    df.withColumn("age", col("age").multiply(2)).show
  }

  it should "map a DF once" in {
    val df = Factory.peopleDf
    df.show
    implicit val mapEncoder = org.apache.spark.sql.Encoders.kryo[Row]
    df.map(row => {
      val index = row.fieldIndex("name")
      val newValue = "Mr. " + row.getString(index)
      val rowSeq = row.toSeq.toBuffer
      rowSeq(index) = newValue
      Row(rowSeq)
    }).foreach(row => println(row))
  }

  it should "map a DF twice" in {
    //    val df = Factory.peopleDf
    //    df.show
    //    val sql = Factory.ss.sqlContext
    //    import sql.implicits._
    //    implicit val mapEncoder = org.apache.spark.sql.Encoders.kryo[Row]
    //    val schema = df.schema
    //    val firstMapRdd = df.map(row => {
    //      val index = row.fieldIndex("name")
    //      val newValue = "Mr. " + row.getString(index)
    //      val rowSeq = row.toSeq.toBuffer
    //      rowSeq(index) = newValue
    //      val newRow = Row(rowSeq)
    //      println("New row: " + newRow)
    //      newRow
    //    }).rdd
    //
    //
    //    val df2 = Factory.ss.createDataFrame(firstMapRdd, schema)
    //    df2.show
    //    df2.map(row => {
    //      val nameIndex = row.fieldIndex("name")
    //      val ageIndex = row.fieldIndex("age")
    //      row.getString(nameIndex) + row.getInt(ageIndex)
    //    }).show

    //      .foreach(row => println(row))
  }

}