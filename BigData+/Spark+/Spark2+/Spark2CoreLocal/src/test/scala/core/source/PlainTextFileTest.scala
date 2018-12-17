package core.source

import core.Factory
import org.scalatest.{FlatSpec, Matchers}

class PlainTextFileTest extends FlatSpec with Matchers {

  it should "init RDD from plain text file" in {
    val uri = getClass.getResource("plain_text_file.txt").toString
    val rdd = Factory.sc.textFile(uri)
    val list = rdd.collect()
    list should contain inOrderOnly("abc", "def")
  }
}
