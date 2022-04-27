package dataframe.create.csv

import factory.Factory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class CsvRead extends AnyFlatSpec with Matchers {

  it should "read a CSV-file in a DataFrame" in {
    val file = getClass.getResource("airports.csv")
    file should not be null

    val df = Factory.ss.sqlContext.read
      .format("com.databricks.spark.csv")
      .option("header", "true")
      .option("inferSchema", "true")
      .load(file.getPath)

    df.printSchema()
    df.show()
  }

}