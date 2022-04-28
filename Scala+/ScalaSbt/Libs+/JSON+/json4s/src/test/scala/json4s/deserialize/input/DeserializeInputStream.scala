package json4s.deserialize.input

import java.io.ByteArrayInputStream

import json4s.City
import org.json4s._
import org.json4s.native.{JsonMethods, Serialization}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class DeserializeInputStream extends AnyFlatSpec with Matchers {

  it should "deserialize JSON from java.io.InputStream" in {
    val is = new ByteArrayInputStream(City.moscowJson.getBytes)
    implicit val formats: AnyRef with Formats = Serialization.formats(NoTypeHints)
    val city: City = JsonMethods.parse(is).extract[City]
    city shouldEqual City.moscowCity
  }
}
