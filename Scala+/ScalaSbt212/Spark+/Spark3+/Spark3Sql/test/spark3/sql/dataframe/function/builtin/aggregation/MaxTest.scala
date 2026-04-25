package spark3.sql.dataframe.function.builtin.aggregation

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.max
import org.scalatest.flatspec.AnyFlatSpec
import spark3.sql.{Factory, SparkMatchers}

class MaxTest extends AnyFlatSpec with SparkMatchers {
  it should "use max function" in {
    val df = Factory.peopleDf
    val updatedDf: DataFrame = df.agg(max("age") as "max_age")
    updatedDf shouldHaveDDL "max_age INT"
    updatedDf shouldContain """{"max_age":35}"""
  }
}