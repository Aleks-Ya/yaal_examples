package dataframe.function.builtin

import factory.Factory
import org.apache.spark.sql.functions.sum
import org.apache.spark.sql.types.LongType
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class SumTest extends AnyFlatSpec with Matchers {
  it should "use sum function" in {
    val df = Factory.peopleDf
    val updatedDf = df.agg(sum("age") as "age_sum")
    updatedDf.schema.fields(updatedDf.schema.fieldIndex("age_sum")).dataType shouldBe LongType
    updatedDf.toJSON.collect should contain only """{"age_sum":80}"""
  }
}