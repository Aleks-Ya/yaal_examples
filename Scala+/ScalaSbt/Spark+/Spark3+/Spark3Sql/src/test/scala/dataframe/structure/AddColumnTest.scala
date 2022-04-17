package dataframe.structure

import factory.Factory
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.StringType
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class AddColumnTest extends AnyFlatSpec with Matchers {

  it should "add a constant column" in {
    val newDf = Factory.peopleDf
      .withColumn("text", lit("aaa"))
      .withColumn("number", lit(777))
    newDf.show
    newDf.printSchema
  }

  it should "add an index column" in {
    val newDf = Factory.peopleDf
      .withColumn("index", monotonically_increasing_id())
    newDf.show
    newDf.printSchema
  }

  it should "add a column based on another column" in {
    val newDf = Factory.peopleDf
      .withColumn("double_age", col("age") * 2)
    newDf.show
    newDf.printSchema
  }

  it should "add a column based on other columns" in {
    val newDf = Factory.peopleDf
      .withColumn("gender_age", col("gender") + "-" + col("age"))
    newDf.show
    newDf.printSchema
  }

  it should "cast another column data type" in {
    val newDf = Factory.peopleDf
      .withColumn("gender_age", (col("gender") + "-" + col("age")).cast(StringType))
    newDf.show
    newDf.printSchema
  }
}