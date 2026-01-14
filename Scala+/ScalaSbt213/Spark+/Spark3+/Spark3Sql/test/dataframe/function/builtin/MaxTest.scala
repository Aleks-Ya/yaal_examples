package dataframe.function.builtin

import factory.Factory
import org.apache.spark.sql.functions.max
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class MaxTest extends AnyFlatSpec with Matchers {
  it should "use max function" in {
    val df = Factory.peopleDf
    val updatedDf = df.agg(max("age") as "max_age")
    updatedDf.toJSON.collect should contain only """{"max_age":35}"""
  }
}