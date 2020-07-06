package json4s.deserialize.input

import java.io.ByteArrayInputStream

import json4s.City
import org.json4s._
import org.json4s.native.{JsonMethods, Serialization}
import org.scalatest.{FlatSpec, Matchers}

class DeserializeInputStream extends FlatSpec with Matchers {

  it should "deserialize JSON from java.io.InputStream" in {
    val is = new ByteArrayInputStream(City.moscowJson.getBytes)
    implicit val formats: AnyRef with Formats = Serialization.formats(NoTypeHints)
    val city: City = JsonMethods.parse(is).extract[City]
    city shouldEqual City.moscowCity
  }
}
