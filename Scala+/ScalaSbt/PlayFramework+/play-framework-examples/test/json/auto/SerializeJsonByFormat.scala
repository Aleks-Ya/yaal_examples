package json.auto

import net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import play.api.libs.json._

/**
 * Serialize JSON with play.libs.Json.
 * Generate Writes automatically.
 */
class SerializeJsonByFormat extends AnyFlatSpec with Matchers {

  it should "serialize case class to JSON (with OFormat)" in {
    implicit val format: OFormat[Person1] = Json.format[Person1]
    val person = Person1("John", 30, List("Moscow", "New York"))
    val jsonValue = Json.toJson(person)
    val jsonStr = Json.stringify(jsonValue)
    assertJsonEquals("""{"name":"John","age":30, "cities": ["Moscow", "New York"]}""", jsonStr)
  }

  it should "serialize case class to JSON (with Writes)" in {
    implicit val writes: OWrites[Person1] = Json.writes[Person1]
    val person = Person1("John", 30, List("Moscow", "New York"))
    val jsonValue = Json.toJson(person)
    val jsonStr = Json.stringify(jsonValue)
    assertJsonEquals("""{"name":"John","age":30, "cities": ["Moscow", "New York"]}""", jsonStr)
  }

  it should "serialize Enumeration (with OFormat)" in {
    implicit val format: OFormat[Person2] = Json.format[Person2] //Gender.format is detected automatically
    val person = Person2("John", 30, Gender.MALE)
    val jsonValue = Json.toJson(person)
    val jsonStr = Json.stringify(jsonValue)
    assertJsonEquals("""{"name":"John","age":30,"gender":"MALE"}""", jsonStr)
  }

  case class Person1(name: String, age: Int, cities: List[String]) //Can't be in test body

  object Gender extends Enumeration {
    type Category = Value
    val MALE, FEMALE = Value
    implicit val format: Format[Gender.Value] = Json.formatEnum(this)
  }

  case class Person2(name: String, age: Int, gender: Gender.Category)
}
