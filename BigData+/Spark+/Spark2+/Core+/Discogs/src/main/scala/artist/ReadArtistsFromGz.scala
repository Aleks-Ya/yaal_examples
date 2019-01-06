package artist

import org.apache.spark.{SparkConf, SparkContext}

import scala.xml.{Elem, Node, XML}

object ReadArtistsFromGz {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName(getClass.getSimpleName)
    val sc = new SparkContext(conf)
    val rdd = sc.textFile("hdfs://master-service:8020/discogs/artists_sample.xml.gz")
    println("RDD: " + rdd.toDebugString)
    sc.stop()
  }

  def xmlToArtist(s: String): Artist = {
    val xml = XML.loadString(s)
    val id = (xml \\ "artist" \\ "id").text
    val name = (xml \\ "artist" \\ "name").text
    val aliases: Iterator[Node] = (xml \\ "artist" \\ "aliases").iterator
    val aliasesList = aliases.map(alias => xmlToAlias(alias)).toList
    new Artist(id, name, aliasesList)
  }

  def xmlToAlias(element: Node): Alias = {
    val name = (element \\ "name").text
    val id = (element \\ "@id").text
    new Alias(id, name)
  }

}