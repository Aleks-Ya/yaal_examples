package core.context

import core.Factory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class PrintSparkContextConfTest extends AnyFlatSpec with Matchers {
  it should "print config of Spark Context" in {
    Factory.sc.getConf.getAll.foreach(println)
  }

  it should "print serializer" in {
    val serializer = Factory.sc.getConf.get("spark.serializer", "org.apache.spark.serializer.JavaSerializer")
    serializer shouldEqual "org.apache.spark.serializer.JavaSerializer"
  }
}
