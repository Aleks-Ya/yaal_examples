package session

import factory.Factory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class PrintSparkSessionConfTest extends AnyFlatSpec with Matchers {
  it should "print config of Spark Session" in {
    Factory.ss.conf.getAll.foreach(println)
  }
}
