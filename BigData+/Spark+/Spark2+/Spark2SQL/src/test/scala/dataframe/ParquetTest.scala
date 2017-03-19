package dataframe

import java.io.File
import java.nio.file.Files

import org.apache.spark.SparkContext
import org.apache.spark.sql._
import org.apache.spark.sql.types._
import org.scalatest.Matchers._
import org.scalatest.{BeforeAndAfterAll, FlatSpec}

class ParquetTest extends FlatSpec with BeforeAndAfterAll {
  var ss: SparkSession = _
  var sc: SparkContext = _
  var sql: SQLContext = _

  override def beforeAll() {
    ss = SparkSession.builder().master("local").getOrCreate()
    sc = ss.sparkContext
    sql = ss.sqlContext
  }

  "Parquet test" should "write to parker file" in {
    val peopleRdd = sc.parallelize(Seq("Jhon,25", "Peter,35"))
    val schemaStr = "name age"
    val schema = StructType(schemaStr.split(" ").map(fieldName => StructField(fieldName, StringType, nullable = true)))
    val rowRdd = peopleRdd.map(_.split(",")).map(p => Row(p(0), p(1).trim))
    val peopleDf = sql.createDataFrame(rowRdd, schema)
    peopleDf.createOrReplaceTempView("people")

    val file = new File(Files.createTempDirectory("parquet").toString, "ouput.parquet")
    peopleDf.write.parquet(file.toString)

    val parquetDf = sql.read.parquet(file.getAbsolutePath)
    parquetDf.show()
    parquetDf.collect.map(_.toString) should contain inOrderOnly("[Jhon,25]", "[Peter,35]")

    parquetDf.createOrReplaceTempView("parquetPeople")
    val resultDf = sql.sql("SELECT name FROM parquetPeople WHERE age > 30")
    resultDf.show()
    resultDf.collect.map(_.toString) should contain("[Peter]")

  }

  override def afterAll() {
    ss.stop()
  }
}