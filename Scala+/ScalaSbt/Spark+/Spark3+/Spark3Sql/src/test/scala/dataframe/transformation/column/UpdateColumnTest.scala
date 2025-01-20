package dataframe.transformation.column

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.catalyst.encoders.{AgnosticEncoder, RowEncoder}
import org.apache.spark.sql.functions._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class UpdateColumnTest extends AnyFlatSpec with Matchers {

  it should "update a column using withColumn transformation" in {
    val df = Factory.peopleDf.withColumn("age", col("age").multiply(2))
    df.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","age":50,"gender":"M"}""",
      """{"name":"Peter","age":70,"gender":"M"}""",
      """{"name":"Mary","age":40,"gender":"F"}""")
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
      """{"name":"Mr. Mary","age":20,"gender":"F"}""")
  }

  it should "rename a nested column" in {
    val df = Factory.createDf("name STRING, details STRUCT<age: INT, gender: STRING>",
      Row("John", Row(35, "M")), Row("Mary", Row(30, "F")))
    val updatedDf = df.withColumn("details", col("details").withField("sex", col("details.gender")).dropFields("gender"))
    updatedDf.schema.simpleString shouldEqual "struct<name:string,details:struct<age:int,sex:string>>"
    updatedDf.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","details":{"age":35,"sex":"M"}}""",
      """{"name":"Mary","details":{"age":30,"sex":"F"}}"""
    )
  }

}