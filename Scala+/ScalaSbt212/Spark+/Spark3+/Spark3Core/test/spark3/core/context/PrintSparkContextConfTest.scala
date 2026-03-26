package spark3.core.context

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark3.core.Factory

class PrintSparkContextConfTest extends AnyFlatSpec with Matchers {

  it should "print config of Spark Context" in {
    Factory.sc.getConf.getAll.foreach(println)
  }

  it should "print serializer" in {
    assertThrows[NoSuchElementException] {
      Factory.sc.getConf.get("spark.serializer")
    }
  }

}
