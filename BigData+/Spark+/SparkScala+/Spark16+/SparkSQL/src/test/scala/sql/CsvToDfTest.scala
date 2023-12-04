package sql

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql._
import org.scalatest.{BeforeAndAfterAll, FlatSpec}
import org.scalatest.Matchers._

class CsvToDfTest extends FlatSpec with BeforeAndAfterAll {

  var sc: SparkContext = _
  var sql: SQLContext = _

  override def beforeAll() {
    val conf = new SparkConf().setAppName("CsvToDfTest").setMaster("local")
    sc = new SparkContext(conf)
    sql = new SQLContext(sc)
  }

  "Read Df from CSV" should "print schema and data table" in {
    val airports = getClass.getResource("airports.csv")
    assert(airports != null)

    val df = sql.read
      .format("com.databricks.spark.csv")
      .option("header", "true")
      .option("inferSchema", "true")
      .load(airports.getPath)

    df.printSchema()
    df.show()
  }

  override def afterAll() {
    sc.stop()
  }
}