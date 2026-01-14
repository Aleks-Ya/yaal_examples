package dataframe.function.builtin

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{array_remove, col}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ArrayRemoveTest extends AnyFlatSpec with Matchers {

  it should "remove a literal from an array" in {
    val df = Factory.createDf("numbers ARRAY<INT>",
      Row(Seq(10, 15, 20)),
      Row(Seq(11, 16, 15)))
    val updatedDf = df.withColumn("clean", array_remove(col("numbers"), 15))
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"numbers":[10,15,20],"clean":[10,20]}""",
      """{"numbers":[11,16,15],"clean":[11,16]}""")
  }

  it should "remove an another column element from an array" in {
    val df = Factory.createDf("numbers ARRAY<INT>, bad_number INT",
      Row(Seq(10, 15, 13), 13),
      Row(Seq(11, 666, 15), 666))
    val updatedDf = df.withColumn("clean", array_remove(col("numbers"), col("bad_number")))
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"numbers":[10,15,13],"bad_number":13,"clean":[10,15]}""",
      """{"numbers":[11,666,15],"bad_number":666,"clean":[11,15]}""")
  }

  //Use `array_compact` instead
  it should "(NOT WORK) remove nulls from an array" in {
    val df = Factory.createDf("numbers ARRAY<INT>",
      Row(Seq(10, null, 20)),
      Row(Seq(null, 16, null)))
    val updatedDf = df.withColumn("clean", array_remove(col("numbers"), null))
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"numbers":[10,null,20],"clean":null}""",
      """{"numbers":[null,16,null],"clean":null}""")
  }
}