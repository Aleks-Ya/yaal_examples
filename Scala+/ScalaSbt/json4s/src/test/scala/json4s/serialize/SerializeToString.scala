package json4s.serialize

import json4s.City
import org.json4s.JsonDSL._
import org.json4s.{NoTypeHints, _}
import org.json4s.native.JsonMethods._
import org.json4s.native.Serialization
import org.json4s.native.Serialization.write
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class SerializeToString extends AnyFlatSpec with Matchers {

  it should "serialize List[Int] to json" in {
    val list = List(1, 2, 3)
    val json = compact(render(list))
    json shouldEqual "[1,2,3]"
  }

  it should "serialize object to json" in {
    implicit val formats: AnyRef with Formats = Serialization.formats(NoTypeHints)
    val json = write(City.moscowCity)
    json shouldEqual City.moscowJson
  }

  it should "serialize java.math.BigDecimal to json" in {
    implicit val formats: AnyRef with Formats = Serialization.formats(NoTypeHints)
    val decimal = java.math.BigDecimal.valueOf(11.22)
    val json = write(decimal)
    json shouldEqual "11.22"
  }

  it should "serialize Map contains java.math.BigDecimal" in {
    implicit val formats: AnyRef with Formats = Serialization.formats(NoTypeHints)
    val valueMap: Map[String, Any] = Map("value" -> java.math.BigDecimal.valueOf(11.22))
    val json = write(valueMap)
    json shouldEqual """{"value":11.22}"""
  }

  it should "serialize null to json" in {
    val json = compact(render(null))
    json shouldEqual "null"
  }

}


