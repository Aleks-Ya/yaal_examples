package sql

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.scalatest.FlatSpec
import org.scalatest.Matchers._
import org.scalatest.BeforeAndAfterAll
import org.apache.spark.sql._
import org.apache.spark.sql.types._
import org.apache.spark.rdd._
import java.nio.file.Files
import java.io.File
import scala.collection.immutable._

class CsvToDfTest extends FlatSpec with BeforeAndAfterAll {

  var sc: SparkContext = null
  var sql: SQLContext = null

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
      .load(airports.getPath.toString)

    df.printSchema()
    df.show()
  }

  override def afterAll() {
    sc.stop()
  }
}