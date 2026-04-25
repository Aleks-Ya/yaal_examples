package spark4.sql.dataframe.function.builtin

import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.expr
import org.scalatest.flatspec.AnyFlatSpec
import spark4.sql.{Factory, SparkMatchers}

class ExprTest extends AnyFlatSpec with SparkMatchers {

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
    updatedDf shouldContain(
      """{"person":"John","sales":[10,20],"sum":30}""",
      """{"person":"Mary","sales":[1,2],"sum":3}""",
      """{"person":"Mark","sales":[3,null],"sum":null}""",
      """{"person":"Matt","sales":null,"sum":null}"""
    )
  }

}