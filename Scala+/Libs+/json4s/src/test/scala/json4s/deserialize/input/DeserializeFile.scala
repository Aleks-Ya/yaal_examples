package json4s.deserialize.input

import java.io.File

import json4s.City
import org.json4s._
import org.json4s.native.{JsonMethods, Serialization}
import org.scalatest.{FlatSpec, Matchers}

class DeserializeFile extends FlatSpec with Matchers {

  it should "deserialize JSON from java.io.File" in {
    val file = new File(this.getClass.getResource("DeserializeFileInput.json").getFile)
    implicit val formats: AnyRef with Formats = Serialization.formats(NoTypeHints)
    val city: City = JsonMethods.parse(file).extract[City]
    city shouldEqual City.moscowCity
  }
}
