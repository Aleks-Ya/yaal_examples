package dataframe.function.builtin

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{array_remove, col}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ArrayRemoveFunction extends AnyFlatSpec with Matchers {
  it should "remove an array element" in {
    val df = Factory.createDf("numbers ARRAY<INT>",
      Row(Seq(10, 15, 20)),
      Row(Seq(11, 16, 15)))
    val updatedDf = df.withColumn("clean", array_remove(col("numbers"), 15))
    updatedDf.toJSON.collect() should contain inOrderOnly(
      """{"numbers":[10,15,20],"clean":[10,20]}""",
      """{"numbers":[11,16,15],"clean":[11,16]}""")
  }

  //Use `array_compact` instead
  it should "(NOT WORK) remove nulls from an array" in {
    val df = Factory.createDf("numbers ARRAY<INT>",
      Row(Seq(10, null, 20)),
      Row(Seq(null, 16, null)))
    val updatedDf = df.withColumn("clean", array_remove(col("numbers"), null))
    updatedDf.toJSON.collect() should contain inOrderOnly(
      """{"numbers":[10,null,20],"clean":null}""",
      """{"numbers":[null,16,null],"clean":null}""")
  }
}