package sql.hive

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.hive._
import org.scalatest.{BeforeAndAfterAll, FlatSpec}
import org.scalatest.Matchers._

class InitHiveContextTest extends FlatSpec with BeforeAndAfterAll {

  "Taste Hive" should "work" in {
    val conf = new SparkConf().setAppName("SqlContextTest").setMaster("local")
    val sc = new SparkContext(conf)
    val hive = new HiveContext(sc)
    hive.sql("CREATE TABLE IF NOT EXISTS src (key INT, value STRING)")
    sc.stop()
  }
}