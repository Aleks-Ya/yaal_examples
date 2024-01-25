package json4s.deserialize

import org.json4s._
import org.json4s.native.{JsonMethods, Serialization}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class DeserializeMap extends AnyFlatSpec with Matchers {

  it should "deserialize Map of Strings" in {
    val json = """{"k1": "v1", "k2": "v2"}"""
    implicit val formats: AnyRef with Formats = Serialization.formats(NoTypeHints)
    val map = JsonMethods.parse(json).extract[Map[String, String]]
    map shouldEqual Map("k1" -> "v1", "k2" -> "v2")
  }

  it should "deserialize Map of Objects" in {
    val json = """{"k1": {"name": "John", "age": 30}, "k2": {"name":"Mary", "age": 25}}"""
    implicit val formats: AnyRef with Formats = Serialization.formats(NoTypeHints)
    val map = JsonMethods.parse(json).extract[Map[String, Person]]
    map.toString shouldEqual "Map(k1 -> Person(John,30), k2 -> Person(Mary,25))"
  }

  case class Person(name: String, age: Int)
}
