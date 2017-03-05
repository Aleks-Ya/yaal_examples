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

class ParuetTest extends FlatSpec with BeforeAndAfterAll {

  var sc: SparkContext = null
  var sql: SQLContext = null

  override def beforeAll() {
    val conf = new SparkConf().setAppName("SqlContextTest").setMaster("local")
    sc = new SparkContext(conf)
    sql = new SQLContext(sc)
  }

  "Parquet test" should "write to parker file" in {
    val peopleRdd = sc.parallelize(Seq("Jhon,25", "Peter,35"))
    val schemaStr = "name age"
    val schema = StructType(schemaStr.split(" ").map(fieldName => StructField(fieldName, StringType, true)))
    val rowRdd = peopleRdd.map(_.split(",")).map(p => Row(p(0), p(1).trim))
    val peopleDf = sql.createDataFrame(rowRdd, schema)
    peopleDf.registerTempTable("people")
    
    val file = new File(Files.createTempDirectory("parquet").toString(), "ouput.parquet")
    peopleDf.write.parquet(file.toString())
    
    val parquetDf = sql.read.parquet(file.getAbsolutePath)
    parquetDf.show()
    parquetDf.map(_.toString).collect should contain inOrderOnly ("[Jhon,25]", "[Peter,35]")
    
    parquetDf.registerTempTable("parquetPeople")
    val resultDf = sql.sql("SELECT name FROM parquetPeople WHERE age > 30")
    resultDf.show()
    resultDf.map(_.toString).collect should contain ("[Peter]")
    
  }

  override def afterAll() {
    sc.stop()
  }
}