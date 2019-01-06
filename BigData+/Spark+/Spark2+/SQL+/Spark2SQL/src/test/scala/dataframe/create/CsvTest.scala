package dataframe.create

import factory.Factory
import org.scalatest.Matchers._
import org.scalatest.{BeforeAndAfterAll, FlatSpec}

class CsvTest extends FlatSpec with BeforeAndAfterAll {

  "Read Df from CSV" should "print schema and data table" in {
    val airports = getClass.getResource("airports.csv")
    airports should not be null

    val df = Factory.ss.sqlContext.read
      .format("com.databricks.spark.csv")
      .option("header", "true")
      .option("inferSchema", "true")
      .load(airports.getPath)

    df.printSchema()
    df.show()
  }

}