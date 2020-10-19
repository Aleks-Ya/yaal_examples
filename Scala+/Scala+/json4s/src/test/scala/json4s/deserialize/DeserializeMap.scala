package json4s.deserialize

import org.json4s._
import org.json4s.native.{JsonMethods, Serialization}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class DeserializeMap extends AnyFlatSpec with Matchers {

  it should "deserialize Map" in {
    val json = """{"k1": "v1", "k2": "v2"}"""
    implicit val formats: AnyRef with Formats = Serialization.formats(NoTypeHints)
    val map = JsonMethods.parse(json).extract[Map[String, String]]
    map shouldEqual Map("k1" -> "v1", "k2" -> "v2")
  }

}
