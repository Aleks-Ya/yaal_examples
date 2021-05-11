package core.source

import core.Factory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class PlainTextFileTest extends AnyFlatSpec with Matchers {

  it should "init RDD from plain text file" in {
    val uri = getClass.getResource("plain_text_file.txt").toString
    val rdd = Factory.sc.textFile(uri)
    val list = rdd.collect()
    list should contain inOrderOnly("abc", "def")
  }
}
