package spark3.sql.dataframe.show

import org.apache.spark.sql.internal.SQLConf
import org.scalatest.flatspec.AnyFlatSpec
import spark3.sql.{Factory, SparkMatchers}

class SessionStateTest extends AnyFlatSpec with SparkMatchers {
  it should "print SQLConf" in {
    val conf: SQLConf = Factory.ss.sessionState.conf
    val map: Map[String, String] = conf.getAllConfs
    println(map)
  }
}