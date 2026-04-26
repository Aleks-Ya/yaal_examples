package spark4.sql.dataframe.operation.transformation.aggregation

import org.apache.spark.sql.Row
import org.apache.spark.sql.functions._
import org.scalatest.flatspec.AnyFlatSpec
import spark4.sql.{Factory, SparkMatchers}

class GroupByPivotTransformationTest extends AnyFlatSpec with SparkMatchers {

  it should "use groupBy with pivot" in {
    val df = Factory.createDf("month STRING, product STRING, amount INT",
      Row("Jan", "Apples", 100),
      Row("Jan", "Bananas", 150),
      Row("Feb", "Apples", 200),
      Row("Feb", "Bananas", 50),
      Row("Jan", "Apples", 50))

    val noPivotDf = df.groupBy("month").agg(sum("amount") as "sum_amount")
    noPivotDf shouldHaveDDL "month STRING,sum_amount BIGINT"
    noPivotDf shouldContain(
      """{"month":"Feb","sum_amount":250}""",
      """{"month":"Jan","sum_amount":300}""")

    val pivotDf = df
      .groupBy("month")
      .pivot("product")
      .agg(sum("amount"))
    pivotDf shouldHaveDDL "month STRING,Apples BIGINT,Bananas BIGINT"
    pivotDf shouldContain(
      """{"month":"Feb","Apples":200,"Bananas":50}""",
      """{"month":"Jan","Apples":150,"Bananas":150}""")
  }

  it should "group by same-case columns" in {
    val df = Factory.createDf("month STRING, product STRING, amount INT",
      Row("Jan", "Apples", 100),
      Row("Jan", "Bananas", 150),
      Row("Feb", "Apples", 200),
      Row("Feb", "BANANAS", 50),
      Row("Jan", "APPLES", 50))

    val noPivotDf = df.groupBy("month").agg(sum("amount") as "sum_amount")
    noPivotDf shouldHaveDDL "month STRING,sum_amount BIGINT"
    noPivotDf shouldContain(
      """{"month":"Feb","sum_amount":250}""",
      """{"month":"Jan","sum_amount":300}""")

    val pivotDf = df
      .groupBy("month")
      .pivot("product")
      .agg(sum("amount"))
    pivotDf shouldHaveDDL "month STRING,APPLES BIGINT,Apples BIGINT,BANANAS BIGINT,Bananas BIGINT"
    pivotDf shouldContain(
      """{"month":"Feb","APPLES":null,"Apples":200,"BANANAS":50,"Bananas":null}""",
      """{"month":"Jan","APPLES":50,"Apples":100,"BANANAS":null,"Bananas":150}""")
  }


}