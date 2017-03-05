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

class TestHiveSingletonTest extends FlatSpec
    with BeforeAndAfterAll
    with TestHiveSingleton {

  "Taste TestHiveSingleton" should "work" in {
    sqlContext.sql("CREATE TABLE IF NOT EXISTS src1 (key INT, value STRING)")
    hiveContext.sql("CREATE TABLE IF NOT EXISTS src2 (key INT, value STRING)")
  }
}