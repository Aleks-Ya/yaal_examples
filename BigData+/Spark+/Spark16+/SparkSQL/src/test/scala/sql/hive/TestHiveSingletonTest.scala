package sql.hive

import org.apache.spark.sql.hive.test._
import org.scalatest.{BeforeAndAfterAll, FlatSpec}
import org.scalatest.Matchers._

class TestHiveSingletonTest extends FlatSpec
  with BeforeAndAfterAll
  with TestHiveSingleton {

  "Taste TestHiveSingleton" should "work" in {
    sqlContext.sql("CREATE TABLE IF NOT EXISTS src1 (key INT, value STRING)")
    hiveContext.sql("CREATE TABLE IF NOT EXISTS src2 (key INT, value STRING)")
  }
}