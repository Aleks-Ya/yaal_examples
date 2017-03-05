package sql.hive

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

class InitHiveContextTest extends FlatSpec with BeforeAndAfterAll {

  "Taste Hive" should "work" in {
    val conf = new SparkConf().setAppName("SqlContextTest").setMaster("local")
    val sc = new SparkContext(conf)
    val hive = new HiveContext(sc)
    hive.sql("CREATE TABLE IF NOT EXISTS src (key INT, value STRING)")
    sc.stop()
  }
}