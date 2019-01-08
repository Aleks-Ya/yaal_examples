package artist

import java.io.File

import org.scalatest.{FlatSpec, Matchers}
import util.Factory

class ArtistGzReadViaSequenceTest extends FlatSpec with Matchers {

  it should "init RDD from text file packaged with GZ" in {
    val gzFile = getClass.getResource("artists_sample_10.xml.gz").getFile
    val sequenceFileUri = File.createTempFile(getClass.getSimpleName, ".seq").toURI
    println("Temp sequence file: " + sequenceFileUri)
    val sc = Factory.sc
    val conf = Factory.sc.hadoopConfiguration
    SplitXmlByArtist.writeSequenceFile(gzFile, sequenceFileUri, sc, conf)
    val rdd = Main.openRddFromSequenceFile(sc, sequenceFileUri.toString)

    val aliasDuplicatesRdd = Main.findDuplicates(rdd)
    val duplicateCount = aliasDuplicatesRdd.count()
    println("Duplicate count: " + duplicateCount)
    println("Error number: " + ArtistXmlToPojoParser.errorCounter)
    //    aliasDuplicatesRdd.foreach(duplicate => println(duplicate))
    //    println("Finished.")
  }

}
