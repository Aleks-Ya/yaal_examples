package dataframe.function.builtin

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.expr
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ExprTest extends AnyFlatSpec with Matchers {

  /**
   * @see AggregateTest
   */
  it should "calculate sum of array column" in {
    val df = Factory.createDf("person STRING, sales ARRAY<INT>",
      Row("John", Seq(10, 20)),
      Row("Mary", Seq(1, 2)),
      Row("Mark", Seq(3, null)),
      Row("Matt", null)
    )
    val updatedDf = df.withColumn("sum", expr("aggregate(sales, 0, (acc, x) -> acc + x)"))
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"person":"John","sales":[10,20],"sum":30}""",
      """{"person":"Mary","sales":[1,2],"sum":3}""",
      """{"person":"Mark","sales":[3,null],"sum":null}""",
      """{"person":"Matt","sales":null,"sum":null}"""
    )
  }

}