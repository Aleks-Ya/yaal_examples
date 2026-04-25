package spark4.sql.dataframe.function.builtin.aggregation

import org.apache.spark.sql.functions.{aggregate, array_compact, col, lit}
import org.apache.spark.sql.{DataFrame, Row}
import org.scalatest.flatspec.AnyFlatSpec
import spark4.sql.{Factory, SparkMatchers}

class AggregateTest extends AnyFlatSpec with SparkMatchers {

  it should "calculate sum of array column" in {
    val df = Factory.createDf("person STRING,sales ARRAY<INT>",
      Row("John", Seq(10, 20)),
      Row("Mary", Seq(1, 2)),
      Row("Mark", Seq(3, null)),
      Row("Matt", null)
    )
    val updatedDf: DataFrame = df.withColumn("sum", aggregate(col("sales"), lit(0), (x, y) => x + y))
    updatedDf shouldHaveDDL "person STRING,sales ARRAY<INT>,sum INT"
    updatedDf shouldContain(
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
    val updatedDf: DataFrame = df.withColumn("sum", aggregate(array_compact(col("sales")), lit(0), (x, y) => x + y))
    updatedDf shouldHaveDDL "person STRING,sales ARRAY<INT>,sum INT"
    updatedDf shouldContain(
      """{"person":"John","sales":[10,20],"sum":30}""",
      """{"person":"Mary","sales":[1,2],"sum":3}""",
      """{"person":"Mark","sales":[3,null],"sum":3}""",
      """{"person":"Matt","sales":null,"sum":null}"""
    )
  }

}