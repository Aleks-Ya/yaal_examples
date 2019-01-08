package artist

import org.apache.hadoop.io.{IntWritable, Text}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Main {

  var errorCounter = 0

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName(getClass.getSimpleName)
    val sc = new SparkContext(conf)
    val rdd = openRddFromSequenceFile(sc, "hdfs://master-service:8020/discogs/artists_sample_100000.xml.gz")
    val aliasDuplicatesRdd = findDuplicates(rdd)
    aliasDuplicatesRdd.foreach(duplicate => println(duplicate))
    println("Error number: " + errorCounter)
    println("Finished.")
    sc.stop()
  }

  def openRddFromSequenceFile(sc: SparkContext, sequenceFilePath: String): RDD[(Int, String)] = {
    sc.sequenceFile(sequenceFilePath,
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