package dataframe

import org.apache.spark.sql._
import org.scalatest.Matchers._
import org.scalatest.{BeforeAndAfterAll, FlatSpec}

class CsvToDfTest extends FlatSpec with BeforeAndAfterAll {

  var ss: SparkSession = _
  var sql: SQLContext = _

  override def beforeAll() {
    ss = SparkSession.builder().master("local").getOrCreate()
    sql = ss.sqlContext
  }

  "Read Df from CSV" should "print schema and data table" in {
    val airports = getClass.getResource("airports.csv")
    airports should not be null

    val df = sql.read
      .format("com.databricks.spark.csv")
      .option("header", "true")
      .option("inferSchema", "true")
      .load(airports.getPath)

    df.printSchema()
    df.show()
  }

  override def afterAll() {
    ss.stop()
  }
}