package json4s.deserialize

import org.json4s._
import org.json4s.native.JsonMethods
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ParseJValueTest extends AnyFlatSpec with Matchers {

  it should "extract value from JValue by path" in {
    val json = """{"k1": {"name": "John", "age": 30}, "k2": {"name":"Mary", "age": 25}}"""
    val rootJValue = JsonMethods.parse(json)
    val ageJValue = rootJValue \ "k1" \ "age"
    implicit val formats: DefaultFormats = DefaultFormats
    val ageInt = ageJValue.extract[Int]
    ageInt shouldEqual 30
  }

}
