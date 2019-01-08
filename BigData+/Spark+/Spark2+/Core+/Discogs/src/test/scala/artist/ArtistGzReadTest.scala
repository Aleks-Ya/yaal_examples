package artist

import org.scalatest.{FlatSpec, Matchers}
import util.Factory

@Deprecated
class ArtistGzReadTest extends FlatSpec with Matchers {

  it should "init RDD from text file packaged with GZ" in {
    val uri = getClass.getResource("artists_sample_10.xml.gz").toString
    val rdd = Factory.sc.textFile(uri)
    val aliasDuplicatesRdd = ReadArtistsFromGz.findDuplicates(rdd)
    aliasDuplicatesRdd.foreach(duplicate => println(duplicate))
    println("Finished.")
  }

}
