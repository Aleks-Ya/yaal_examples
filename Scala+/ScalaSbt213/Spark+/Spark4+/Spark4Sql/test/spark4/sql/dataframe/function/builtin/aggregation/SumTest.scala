package spark4.sql.dataframe.function.builtin.aggregation

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.sum
import org.scalatest.flatspec.AnyFlatSpec
import spark4.sql.{Factory, SparkMatchers}

class SumTest extends AnyFlatSpec with SparkMatchers {
  it should "use sum function" in {
    val df = Factory.peopleDf
    val updatedDf: DataFrame = df.agg(sum("age") as "age_sum")
    updatedDf shouldHaveDDL "age_sum BIGINT"
    updatedDf shouldContain """{"age_sum":80}"""
  }
}