package dataframe.transformation

import factory.Factory
import org.apache.spark.sql.functions
import org.apache.spark.sql.functions.sum
import org.apache.spark.sql.types.LongType
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class AggTransformation extends AnyFlatSpec with Matchers {

  it should "calculate sum with an agg transformation" in {
    val df = Factory.peopleDf.agg(sum("age"))
    df.schema.fields(df.schema.fieldIndex("sum(age)")).dataType shouldBe LongType
    df.toJSON.collect() should contain only """{"sum(age)":80}"""
  }

  it should "rename column after an agg transformation" in {
    val df = Factory.peopleDf.agg(sum("age").as("age_sum"))
    df.schema.fields(df.schema.fieldIndex("age_sum")).dataType shouldBe LongType
    df.toJSON.collect() should contain only """{"age_sum":80}"""
  }
}