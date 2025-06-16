package dataframe.datatype

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * Handle percents (%).
 */
class Percents extends AnyFlatSpec with Matchers {

  it should "calculate percents" in {
    val df = Factory.createDf("name STRING,production INT", Row("USA", 50), Row("Canada", 25))
    val totalProduction = df.agg(sum("production")).first().getLong(0)
    val percentDf = df
      .withColumn("percentDouble0", col("production") / totalProduction)
      .withColumn("percentDouble2", round(col("percentDouble0") * 100, 2))
      .withColumn("percentText", concat(col("percentDouble2"), lit("%")))
    percentDf.schema.toDDL shouldEqual "name STRING,production INT,percentDouble0 DOUBLE,percentDouble2 DOUBLE,percentText STRING"
    percentDf.toJSON.collect should contain inOrderOnly(
      """{"name":"USA","production":50,"percentDouble0":0.6666666666666666,"percentDouble2":66.67,"percentText":"66.67%"}""",
      """{"name":"Canada","production":25,"percentDouble0":0.3333333333333333,"percentDouble2":33.33,"percentText":"33.33%"}"""
    )
  }

}