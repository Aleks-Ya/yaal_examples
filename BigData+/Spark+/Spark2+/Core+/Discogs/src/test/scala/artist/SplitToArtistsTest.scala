package artist

import java.io._

import org.apache.hadoop.fs.FileSystem
import org.scalatest.{FlatSpec, Matchers}
import util.Factory

class SplitToArtistsTest extends FlatSpec with Matchers {

  it should "convert XML gz archive to a sequence file with artists" in {
    val gzFile = getClass.getResource("artists_sample_10.xml.gz").getFile
    val seqFile = File.createTempFile(getClass.getSimpleName, ".seq").getAbsolutePath
    println("Temp sequence file: " + seqFile)
    val sc = Factory.sc
    val conf = Factory.sc.hadoopConfiguration
    val hadoopConf = sc.hadoopConfiguration
    val fs = FileSystem.get(hadoopConf)

    val discogsConf = new DiscogsConf(sc, fs, hadoopConf, gzFile, seqFile)

    SplitXmlByArtist.convertXmlGzToSequenceFile(discogsConf)

    val actMap = SplitXmlByArtist.readToMap(discogsConf)
    println("Map size:" + actMap.size())
    actMap should have size 10
  }

}
