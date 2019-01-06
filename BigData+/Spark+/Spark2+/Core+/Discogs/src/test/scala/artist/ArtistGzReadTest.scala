package artist

import org.scalatest.{FlatSpec, Matchers}
import util.Factory

class ArtistGzReadTest extends FlatSpec with Matchers {

  it should "init RDD from text file packaged with GZ" in {
    val uri = getClass.getResource("artists_sample_10.xml.gz").toString
    val rdd = Factory.sc.textFile(uri)
      .filter(s => !s.contains("<artists>") && !s.contains("</artists>"))
      .map(s => ReadArtistsFromGz.xmlToArtist(s))
    val artists = rdd.collect()
    artists.foreach((artist: Artist) => println(artist))
  }

}
