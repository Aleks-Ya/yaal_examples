package sql.hive

import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.hive._
import org.apache.spark.sql.hive.test.{TestHive, TestHiveContext}
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}
import org.scalatest.Matchers._
import org.scalatest.events.TestIgnored

class InitHiveContextTest extends FlatSpec
  //  with BeforeAndAfterAll
  with Matchers {

  //  "Taste Hive" should "work" in {
  //    val conf = new SparkConf().setAppName("SqlContextTest").setMaster("local")
  //    val sc = new SparkContext(conf)
  //    val testHive = new org.apache.spark.sql.hive.test.TestHiveContext(sc, false)
  //    val hiveClient = testHive.sessionState.metadataHive
  //    hiveClient.runSqlHive("CREATE TABLE IF NOT EXISTS src (key INT, value STRING)")
  //  }

  //  "Taste Hive 2" should "work" in {
  //        val hiveSession = TestHive.sparkSession.newSession()
  //        hiveSession.sql("CREATE TABLE IF NOT EXISTS src (key INT, value STRING)")
  //  }

//  "Taste Hive 3" should "work" in {
//    val ss = SparkSession.builder().master("local").getOrCreate
//    val sc = ss.sparkContext
//    val hive = new TestHiveContext(sc)
//    val df = hive.sql("CREATE TABLE IF NOT EXISTS src (key INT, value STRING)")
//    //        hive.sql("CREATE TABLE IF NOT EXISTS src (key INT, value STRING)")
//  }
}