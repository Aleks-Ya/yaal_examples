package artist

import org.apache.hadoop.fs.FileSystem
import org.scalatest.{FlatSpec, Matchers}
import util.Factory

class ArtistGzReadViaSequenceTest extends FlatSpec with Matchers {

  it should "init RDD from text file packaged with GZ" in {
    val gzFile = getClass.getResource("artists_sample_10.xml.gz").getFile
    val seqFile = "file:///tmp/discogs/artists.seq"
    val sc = Factory.sc
    val hadoopConf = Factory.sc.hadoopConfiguration
    val fs = FileSystem.get(hadoopConf)

    val conf = new DiscogsConf(sc, fs, hadoopConf, gzFile, seqFile)

    val duplicateCount = Main.countDuplicates(conf)

    println("Duplicate count: " + duplicateCount)
    println("Error number: " + ArtistXmlToPojoParser.errorCounter)
    duplicateCount shouldEqual 2
    ArtistXmlToPojoParser.errorCounter shouldEqual 0
  }

}
