package spark3.sql.dataframe.show

import org.apache.spark.sql.internal.SQLConf
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark3.sql.Factory

class SessionStateTest extends AnyFlatSpec with Matchers {
  it should "print SQLConf" in {
    val conf: SQLConf = Factory.ss.sessionState.conf
    val map: Map[String, String] = conf.getAllConfs
    println(map)
  }
}