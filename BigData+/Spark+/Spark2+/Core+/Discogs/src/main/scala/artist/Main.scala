package artist

import java.net.URI

import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.hadoop.io.{IntWritable, Text}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Main {

  var errorCounter = 0

  def main(args: Array[String]): Unit = {
    println("Starting Main#main...")

    var gzFile = "hdfs://master-service:8020/discogs/artists_sample_100000.xml.gz"
    if (args.length > 0) {
      gzFile = args(0)
    }
    println("gzFile: " + gzFile)

    val sparkConf = new SparkConf().setAppName(getClass.getSimpleName)
    val sc = new SparkContext(sparkConf)
    val hadoopConf = sc.hadoopConfiguration
    println("Hadoop config: " + hadoopConf)
    val fs = FileSystem.get(new URI("hdfs://master-service:8020/"), hadoopConf, "root")
    val seqFile = "hdfs://master-service:8020/discogs/artists.seq"

    val discogsConf = new DiscogsConf(sc, fs, hadoopConf, gzFile, seqFile)

    val duplicateCount = countDuplicates(discogsConf)
    println("Duplicate count: " + duplicateCount)
    println("Error number: " + errorCounter)

    sc.stop()
  }

  def countDuplicates(conf: DiscogsConf): Long = {
    val fs = conf.fs
    val sequenceFileUri = new URI(conf.seqFile)
    val sequenceFilePath = new Path(sequenceFileUri)
    if (fs.exists(sequenceFilePath)) {
      fs.delete(sequenceFilePath, false)
    }
    println("Writing the sequence file...")
    SplitXmlByArtist.convertXmlGzToSequenceFile(conf)
    println("Creating RDD from the sequence file...")
    val rdd = openRddFromSequenceFile(conf)
    println("Finding duplicates...")
    val aliasDuplicatesRdd = findDuplicates(rdd)
    //    aliasDuplicatesRdd.foreach(duplicate => println(duplicate))
    aliasDuplicatesRdd.count()
  }

  def openRddFromSequenceFile(conf: DiscogsConf): RDD[(Int, String)] = {
    conf.sc.sequenceFile(conf.seqFile,
      classOf[IntWritable], classOf[Text])
      .map { case (x, y) => (x.get(), y.toString) }
  }


  def findDuplicates(rdd: RDD[(Int, String)]): RDD[String] = {
    rdd.map(tuple => tuple._2)
      .map(s => ArtistXmlToPojoParser.xmlToArtist(s))
      .filter(option => option.isDefined)
      .map(option => option.get)
      .flatMap(artist => artist.aliases.map(alias => (alias.name.toLowerCase, artist)))
      .groupBy(aliasArtistTuple => aliasArtistTuple._1)
      .filter(aliasArtistsTuple => aliasArtistsTuple._2.size > 1)
      .map(aliasArtistsTuple => formatAliasArtistsTuple(aliasArtistsTuple))
  }

  def formatAliasArtistsTuple(aliasArtistsTuple: (String, scala.Iterable[(String, Artist)])): String = {
    val artists = aliasArtistsTuple._2.map(aliasArtistsTuple => s"${aliasArtistsTuple._2.name} (${aliasArtistsTuple._2.id})").mkString(", ")
    s"${aliasArtistsTuple._1}: $artists"
  }

}