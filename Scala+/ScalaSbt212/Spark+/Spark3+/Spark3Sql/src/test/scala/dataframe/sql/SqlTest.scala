package dataframe.sql

import factory.Factory
import org.apache.spark.sql.Row
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class SqlTest extends AnyFlatSpec with Matchers {

  it should "sum numbers" in {
    val df = Factory.ss.sql("SELECT 1 + 1 AS result")
    df.toJSON.collect should contain only """{"result":2}"""
  }

  it should "read temp view" in {
    Factory.peopleDf.createTempView("population")
    val df = Factory.ss.sql("SELECT MAX(age) AS max_age FROM population")
    df.toJSON.collect should contain only """{"max_age":35}"""
  }

  it should "use collect_set" in {
    Factory.createDf("city STRING",
      Row("London"),
      Row("Paris"),
      Row("London")
    ).createTempView("cities")
    val df = Factory.ss.sql("SELECT collect_set(city) AS unique_cities FROM cities")
    df.toJSON.collect should contain only """{"unique_cities":["Paris","London"]}"""
  }

}