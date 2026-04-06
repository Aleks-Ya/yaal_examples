package spark4.core.rdd.create

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark4.core.Factory

class GzFileTest extends AnyFlatSpec with Matchers {

  it should "init RDD from text file packaged with GZ" in {
    val uri = getClass.getResource("gz_file.txt.gz").toString
    val rdd = Factory.sc.textFile(uri)
    val list = rdd.collect()
    list should contain inOrderOnly("abc", "def")
  }
}
