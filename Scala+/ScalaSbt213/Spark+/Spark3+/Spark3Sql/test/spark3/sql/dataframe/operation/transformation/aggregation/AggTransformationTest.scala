package spark3.sql.dataframe.operation.transformation.aggregation

import org.apache.spark.sql.functions.{count, max, sum}
import org.scalatest.flatspec.AnyFlatSpec
import spark3.sql.{Factory, SparkMatchers}


class AggTransformationTest extends AnyFlatSpec with SparkMatchers {

  it should "calculate sum with an agg transformation" in {
    val df = Factory.peopleDf
    val updatedDf = df.agg(sum("age"))
    updatedDf shouldHaveDDL "`sum(age)` BIGINT"
    updatedDf shouldContain """{"sum(age)":80}"""
  }

  it should "rename column after an agg transformation" in {
    val df = Factory.peopleDf
    val updatedDf = df.agg(sum("age").as("age_sum"))
    updatedDf shouldHaveDDL "age_sum BIGINT"
    updatedDf shouldContain """{"age_sum":80}"""
  }

  it should "calculate several aggregations" in {
    val df = Factory.peopleDf
    val updatedDf = df.agg(sum("age"), max("age"))
    updatedDf shouldHaveDDL "`sum(age)` BIGINT,`max(age)` INT"
    updatedDf shouldContain """{"sum(age)":80,"max(age)":35}"""
  }

  it should "calculate count with an agg transformation" in {
    val df = Factory.peopleDf
    val updatedDf = df.agg(count("age"))
    updatedDf shouldHaveDDL "`count(age)` BIGINT NOT NULL"
    updatedDf shouldContain """{"count(age)":3}"""
  }

}