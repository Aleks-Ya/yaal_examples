package sql

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.scalatest.FlatSpec
import org.scalatest.Matchers._
import org.scalatest.BeforeAndAfterAll
import org.apache.spark.sql._
import org.apache.spark.sql.hive._
import org.apache.spark.sql.hive.test._
import org.apache.spark.sql.types._
import org.apache.spark.rdd._
import java.nio.file._

class HiveTest extends FlatSpec with BeforeAndAfterAll with TestHiveSingleton {

//  var sc: SparkContext = null
//  var sql: SQLContext = null
//  var hive: HiveContext = null

//  override def beforeAll() {
//    val conf = new SparkConf().setAppName("SqlContextTest").setMaster("local")
//    sc = new SparkContext(conf)
//    sql = new SQLContext(sc)
//    
//    hive = new HiveContext(sc)
//    println("scratch: " + Files.createTempDirectory("hive").toString())
//    hive.setConf("hive.exec.scratchdir", Files.createTempDirectory("hive").toString())
    
//  }

//  "Taste Hive" should "work" in {
//	  val tmp = Files.createTempDirectory("hive").toString()
//    println("scratch: " + tmp)
//    val conf = new SparkConf().setAppName("SqlContextTest").setMaster("local")
//    .set("hive.exec.local.scratchdir", tmp)
//    .set("hive.exec.scratchdir", """c:\Users\Aleksei_Iablokov\tmp\hive\""")
//    sc = new SparkContext(conf)
//    sql = new SQLContext(sc)
    
//	  new TestHive()
//    hive = new HiveContext(sc)
//    hive.setConf("hive.exec.scratchdir", Files.createTempDirectory("hive").toString())
//    val hive = hiveContext
//    hive.sql("CREATE TABLE IF NOT EXISTS src (key INT, value STRING)")
//  }

//  override def afterAll() {
//    sc.stop()
//  }
}