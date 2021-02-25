package json.auto

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import play.api.libs.json._

/**
 * Serialize JSON with play.libs.Json.
 * Generate Writes automatically.
 */
class SerializeJsonByFormat extends AnyFlatSpec with Matchers {

  case class Person1(name: String, age: Int) //Can't be in test body

  it should "serialize case class to JSON" in {
    implicit val personFormat: OFormat[Person1] = Json.format[Person1]
    val person = Person1("John", 30)
    val jsonValue = Json.toJson(person)
    val jsonStr = Json.stringify(jsonValue)
    jsonStr shouldEqual """{"name":"John","age":30}"""
  }

  object Gender extends Enumeration {
    type Category = Value
    val MALE, FEMALE = Value
    implicit val format: Format[Gender.Value] = Json.formatEnum(this)
  }

  case class Person2(name: String, age: Int, gender: Gender.Category)

  it should "serialize Enumeration" in {
    implicit val personFormat: OFormat[Person2] = Json.format[Person2] //Gender.format is detected automatically
    val person = Person2("John", 30, Gender.MALE)
    val jsonValue = Json.toJson(person)
    val jsonStr = Json.stringify(jsonValue)
    jsonStr shouldEqual """{"name":"John","age":30,"gender":"MALE"}"""
  }
}
