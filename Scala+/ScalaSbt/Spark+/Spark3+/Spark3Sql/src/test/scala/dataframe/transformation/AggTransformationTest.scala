package dataframe.transformation

import factory.Factory
import org.apache.spark.sql.functions.{count, max, sum}
import org.apache.spark.sql.types.LongType
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class AggTransformationTest extends AnyFlatSpec with Matchers {

  it should "calculate sum with an agg transformation" in {
    val df = Factory.peopleDf
    val updatedDf = df.agg(sum("age"))
    updatedDf.schema.fields(updatedDf.schema.fieldIndex("sum(age)")).dataType shouldBe LongType
    updatedDf.toJSON.collect should contain only """{"sum(age)":80}"""
  }

  it should "rename column after an agg transformation" in {
    val df = Factory.peopleDf
    val updatedDf = df.agg(sum("age").as("age_sum"))
    updatedDf.schema.fields(updatedDf.schema.fieldIndex("age_sum")).dataType shouldBe LongType
    updatedDf.toJSON.collect should contain only """{"age_sum":80}"""
  }

  it should "calculate several aggregations" in {
    val df = Factory.peopleDf
    val updatedDf = df.agg(sum("age"), max("age"))
    updatedDf.toJSON.collect should contain only """{"sum(age)":80,"max(age)":35}"""
  }

  it should "calculate count with an agg transformation" in {
    val df = Factory.peopleDf
    val updatedDf = df.agg(count("age"))
    updatedDf.schema.fields(updatedDf.schema.fieldIndex("count(age)")).dataType shouldBe LongType
    updatedDf.toJSON.collect should contain only """{"count(age)":3}"""
  }
}