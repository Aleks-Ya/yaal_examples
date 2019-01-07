package artist

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.xml.{Node, XML}

object ReadArtistsFromGz {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName(getClass.getSimpleName)
    val sc = new SparkContext(conf)
    val rdd = sc.textFile("hdfs://master-service:8020/discogs/artists_sample_100000.xml.gz")
    val aliasDuplicatesRdd = findDuplicates(rdd)
    aliasDuplicatesRdd.foreach(duplicate => println(duplicate))
    println("Finished.")
    sc.stop()
  }

  def findDuplicates(rdd: RDD[String]): RDD[String] = {
    rdd.filter(s => !s.contains("<artists>") && !s.contains("</artists>"))
      .map(s => ReadArtistsFromGz.xmlToArtist(s))
      .flatMap(artist => artist.aliases.map(alias => (alias.name.toLowerCase, artist)))
      .groupBy(aliasArtistTuple => aliasArtistTuple._1)
      .filter(aliasArtistsTuple => aliasArtistsTuple._2.size > 1)
      .map(aliasArtistsTuple => formatAliasArtistsTuple(aliasArtistsTuple))
  }

  def formatAliasArtistsTuple(aliasArtistsTuple: (String, scala.Iterable[(String, Artist)])): String = {
    val artists = aliasArtistsTuple._2.map(aliasArtistsTuple => s"${aliasArtistsTuple._2.name} (${aliasArtistsTuple._2.id})").mkString(", ")
    s"${aliasArtistsTuple._1}: $artists"

  }

  def xmlToArtist(s: String): Artist = {
    val xml = XML.loadString(s)
    val id = (xml \ "id").text
    val name = (xml \ "name").text
    val aliases: Iterator[Node] = (xml \ "aliases" \ "name").iterator
    val aliasesList = aliases.map(alias => xmlToAlias(alias)).toList
    new Artist(id, name, aliasesList)
  }

  def xmlToAlias(element: Node): Alias = {
    val name = element.text
    val id = (element \ "@id").text
    new Alias(id, name)
  }

}