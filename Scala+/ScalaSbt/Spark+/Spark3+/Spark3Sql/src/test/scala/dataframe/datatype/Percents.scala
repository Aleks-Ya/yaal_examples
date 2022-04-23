package dataframe.datatype

import factory.Factory
import org.apache.spark.sql.functions.{col, concat, lit, round}
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{Row, functions}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * Handle percents (%).
 */
class Percents extends AnyFlatSpec with Matchers {

  it should "calculate percents" in {
    val schema = StructType(
      StructField("name", StringType, nullable = true) ::
        StructField("production", IntegerType, nullable = true) :: Nil)
    val rowRdd = Factory.ss.sparkContext.parallelize(Seq("USA,50", "Canada,25"))
      .map(_.split(",")).map(p => Row(p(0), p(1).toInt))
    val df = Factory.ss.sqlContext.createDataFrame(rowRdd, schema)
    df.show()

    val totalProduction = df.agg(functions.sum("production")).first().getLong(0)
    val percentDf = df
      .withColumn("percentDouble0", col("production") / totalProduction)
      .withColumn("percentDouble2", round(col("percentDouble0") * 100, 2))
      .withColumn("percentText", concat(col("percentDouble2"), lit("%")))
    percentDf.show()
    percentDf.printSchema()
    percentDf.toJSON.collect() should contain inOrderOnly(
      """{"name":"USA","production":50,"percentDouble0":0.6666666666666666,"percentDouble2":66.67,"percentText":"66.67%"}""",
      """{"name":"Canada","production":25,"percentDouble0":0.3333333333333333,"percentDouble2":33.33,"percentText":"33.33%"}"""
    )
  }

}