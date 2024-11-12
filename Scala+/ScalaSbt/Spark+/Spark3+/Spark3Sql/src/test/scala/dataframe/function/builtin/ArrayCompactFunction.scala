package dataframe.function.builtin

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{array_compact, col}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ArrayCompactFunction extends AnyFlatSpec with Matchers {
  it should "remove nulls from an array" in {
    val df = Factory.createDf("numbers ARRAY<INT>",
      Row(Seq(10, null, 20)),
      Row(Seq(null, 16, null)),
      Row(Seq()),
      Row(null))
    val updatedDf = df.withColumn("clean", array_compact(col("numbers")))
    updatedDf.toJSON.collect() should contain inOrderOnly(
      """{"numbers":[10,null,20],"clean":[10,20]}""",
      """{"numbers":[null,16,null],"clean":[16]}""",
      """{"numbers":[],"clean":[]}""",
      """{"numbers":null,"clean":null}""")
  }
}