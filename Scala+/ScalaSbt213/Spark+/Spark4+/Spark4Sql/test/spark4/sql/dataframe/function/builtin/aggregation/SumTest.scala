package spark4.sql.dataframe.function.builtin.aggregation

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.sum
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark4.sql.Factory

class SumTest extends AnyFlatSpec with Matchers {
  it should "use sum function" in {
    val df = Factory.peopleDf
    val updatedDf: DataFrame = df.agg(sum("age") as "age_sum")
    updatedDf.schema.toDDL shouldEqual "age_sum BIGINT"
    updatedDf.toJSON.collect should contain only """{"age_sum":80}"""
  }
}