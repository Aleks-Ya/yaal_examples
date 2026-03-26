package spark3.sql.session

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark3.sql.Factory

class PrintSparkSessionConfTest extends AnyFlatSpec with Matchers {
  it should "print config of Spark Session" in {
    Factory.ss.conf.getAll.foreach(println)
  }
}
