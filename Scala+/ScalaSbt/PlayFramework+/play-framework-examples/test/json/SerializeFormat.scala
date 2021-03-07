package json

import net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import play.api.libs.json._

class SerializeFormat extends AnyFlatSpec with Matchers {

  it should "serialize a case class to JSON (automated mapping)" in {
    implicit val format: OFormat[Person1] = Json.format[Person1]
    val person = Person1("John", 30, Gender.MALE, List("Moscow", "New York"))
    val jsonValue = Json.toJson(person)
    val jsonStr = Json.stringify(jsonValue)
    assertJsonEquals("""{"name":"John","age":30,"gender":"MALE", "cities": ["Moscow", "New York"]}""", jsonStr)
  }

  case class Person1(name: String, age: Int, gender: Gender.Category, cities: List[String]) //Can't be in test body

  object Gender extends Enumeration {
    type Category = Value
    val MALE, FEMALE = Value
    implicit val format: Format[Gender.Value] = Json.formatEnum(this)
  }

}
