package dataframe.structure

import dataframe.DfFactory
import org.apache.spark.sql.functions._
import org.scalatest.{FlatSpec, Matchers}

class AddColumnTest extends FlatSpec with Matchers {

  "Apply columns to DF" should "works" in {
    val newDf = DfFactory.peopleDf
      .withColumn("text", lit("aaa"))
      .withColumn("number", lit(777))
    newDf.show
    newDf.printSchema
  }

  "Apply index columns" should "works" in {
    val newDf = DfFactory.peopleDf
      .withColumn("index", monotonically_increasing_id())
    newDf.show
    newDf.printSchema
  }
}