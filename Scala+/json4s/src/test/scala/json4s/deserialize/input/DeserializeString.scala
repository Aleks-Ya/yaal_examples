package json4s.deserialize.input

import json4s.City
import org.json4s._
import org.json4s.native.{JsonMethods, Serialization}
import org.scalatest.{FlatSpec, Matchers}

class DeserializeString extends FlatSpec with Matchers {

  it should "deserialize JSON from String" in {
    implicit val formats: AnyRef with Formats = Serialization.formats(NoTypeHints)
    val city: City = JsonMethods.parse(City.moscowJson).extract[City]
    city shouldEqual City.moscowCity
  }

}
