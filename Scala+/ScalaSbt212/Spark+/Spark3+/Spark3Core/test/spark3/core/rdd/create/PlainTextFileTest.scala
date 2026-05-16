package spark3.core.rdd.create

import org.apache.spark.rdd.RDD
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark3.core.Factory

class PlainTextFileTest extends AnyFlatSpec with Matchers {

  it should "read text file to RDD" in {
    val path: String = getClass.getResource("plain_text_file.txt").toString
    val rdd: RDD[String] = Factory.sc.textFile(path)
    val list: Array[String] = rdd.collect
    list should contain inOrderOnly("abc", "def")
  }

}
