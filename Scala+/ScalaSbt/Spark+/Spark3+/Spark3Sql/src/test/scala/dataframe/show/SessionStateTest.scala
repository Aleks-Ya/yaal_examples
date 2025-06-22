package dataframe.show

import factory.Factory
import org.apache.spark.sql.internal.SQLConf
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class SessionStateTest extends AnyFlatSpec with Matchers {
  it should "print SQLConf" in {
    val conf: SQLConf = Factory.ss.sessionState.conf
    val map: Map[String, String] = conf.getAllConfs
    println(map)
  }
}