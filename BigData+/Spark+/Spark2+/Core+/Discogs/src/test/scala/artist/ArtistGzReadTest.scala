package artist

import org.scalatest.{FlatSpec, Matchers}
import util.Factory

class ArtistGzReadTest extends FlatSpec with Matchers {

  it should "init RDD from text file packaged with GZ" in {
    val uri = getClass.getResource("artists_sample.xml.gz").toString
    val rdd = Factory.sc.textFile(uri)
    val list = rdd.collect()
    list.foreach((s: String) => println("String: " + s))
  }
}
