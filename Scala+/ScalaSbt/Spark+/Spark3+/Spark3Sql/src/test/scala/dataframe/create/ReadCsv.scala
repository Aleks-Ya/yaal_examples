package dataframe.create

import factory.Factory
import org.scalatest.BeforeAndAfterAll
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ReadCsv extends AnyFlatSpec with Matchers with BeforeAndAfterAll {

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