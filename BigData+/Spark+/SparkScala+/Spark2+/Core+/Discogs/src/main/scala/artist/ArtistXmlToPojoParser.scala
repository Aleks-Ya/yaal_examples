package artist

import scala.xml.{Node, XML}

object ArtistXmlToPojoParser {

  var errorCounter = 0

  def xmlToArtist(s: String): Option[Artist] = {
    try {
      val xml = XML.loadString(s)
      val id = (xml \ "id").text
      val name = (xml \ "name").text
      val aliases: Iterator[Node] = (xml \ "aliases" \ "name").iterator
      val aliasesList = aliases.map(alias => xmlToAlias(alias)).toList
      Some(new Artist(id, name, aliasesList))
    } catch {
      case e: Exception =>
        errorCounter += 1
        println(s"Can't parse: $s\n${e.getMessage}")
        None
    }
  }

  def xmlToAlias(element: Node): Alias = {
    val name = element.text
    val id = (element \ "@id").text
    new Alias(id, name)
  }

}