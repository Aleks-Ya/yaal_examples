package dataframe.create.csv

import factory.Factory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * Read Tab Separated Values (TSV) format.
 */
class TsvRead extends AnyFlatSpec with Matchers {

  it should "read a TSV-file to a DataFrame" in {
    val file = getClass.getResource("airports.tsv")
    file should not be null

    val df = Factory.ss.sqlContext.read
      .format("com.databricks.spark.csv")
      .option("header", "true")
      .option("inferSchema", "true")
      .option("delimiter", "\t")
      .load(file.getPath)

    df.printSchema()
    df.show()
  }

  it should "read a GZIP-compressed TSV-file to a DataFrame" in {
    val file = getClass.getResource("airports.tsv.gz")
    file should not be null

    val df = Factory.ss.sqlContext.read
      .format("com.databricks.spark.csv")
      .option("header", "true")
      .option("inferSchema", "true")
      .option("delimiter", "\t")
      .option("compression", "gzip")
      .load(file.getPath)

    df.printSchema()
    df.show()
  }


}