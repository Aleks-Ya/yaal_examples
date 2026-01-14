package dataframe.function.builtin

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{aggregate, array_compact, col, lit}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class AggregateTest extends AnyFlatSpec with Matchers {

  it should "calculate sum of array column" in {
    val df = Factory.createDf("person STRING,sales ARRAY<INT>",
      Row("John", Seq(10, 20)),
      Row("Mary", Seq(1, 2)),
      Row("Mark", Seq(3, null)),
      Row("Matt", null)
    )
    val updatedDf = df.withColumn("sum", aggregate(col("sales"), lit(0), (x, y) => x + y))
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"person":"John","sales":[10,20],"sum":30}""",
      """{"person":"Mary","sales":[1,2],"sum":3}""",
      """{"person":"Mark","sales":[3,null],"sum":null}""",
      """{"person":"Matt","sales":null,"sum":null}"""
    )
  }

  it should "calculate sum of array column (replace null with zero)" in {
    val df = Factory.createDf("person STRING,sales ARRAY<INT>",
      Row("John", Seq(10, 20)),
      Row("Mary", Seq(1, 2)),
      Row("Mark", Seq(3, null)),
      Row("Matt", null)
    )
    val updatedDf = df.withColumn("sum", aggregate(array_compact(col("sales")), lit(0), (x, y) => x + y))
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"person":"John","sales":[10,20],"sum":30}""",
      """{"person":"Mary","sales":[1,2],"sum":3}""",
      """{"person":"Mark","sales":[3,null],"sum":3}""",
      """{"person":"Matt","sales":null,"sum":null}"""
    )
  }

}