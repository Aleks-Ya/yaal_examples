package dataframe.transformation.column

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.catalyst.encoders.{AgnosticEncoder, ExpressionEncoder, RowEncoder}
import org.apache.spark.sql.functions._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class UpdateColumnTest extends AnyFlatSpec with Matchers {

  it should "update a column using withColumn transformation" in {
    val df = Factory.peopleDf.withColumn("age", col("age").multiply(2))
    df.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","age":50,"gender":"M"}""",
      """{"name":"Peter","age":70,"gender":"M"}""",
      """{"name":"Mary","age":40,"gender":"F"}"""
    )
  }

  it should "update a column using map transformation" in {
    implicit val encoder: AgnosticEncoder[Row] = RowEncoder.encoderFor(Factory.peopleDf.schema)
    val df = Factory.peopleDf.map(row => {
      val nameIndex = row.fieldIndex("name")
      val oldValue = row.getString(nameIndex)
      val newValue = "Mr. " + oldValue
      val rowSeq = row.toSeq.toBuffer
      rowSeq(nameIndex) = newValue
      Row.fromSeq(rowSeq)
    })
    df.toJSON.collect() should contain inOrderOnly(
      """{"name":"Mr. John","age":25,"gender":"M"}""",
      """{"name":"Mr. Peter","age":35,"gender":"M"}""",
      """{"name":"Mr. Mary","age":20,"gender":"F"}"""
    )
  }

}