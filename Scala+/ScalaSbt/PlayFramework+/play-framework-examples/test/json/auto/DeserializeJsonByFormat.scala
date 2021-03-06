package json.auto

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import play.api.libs.json._

/**
 * Deserialize JSON with play.libs.Json.
 * Generate Reads automatically.
 */
class DeserializeJsonByFormat extends AnyFlatSpec with Matchers {

  it should "deserialize case class to JSON (with OFormat)" in {
    implicit val format: OFormat[Person1] = Json.format[Person1]
    val json = """{"name":"John","age":30, "cities": ["Moscow", "New York"]}"""
    val jsValue = Json.parse(json)
    val personAct = jsValue.as[Person1]
    val personExp = Person1("John", 30, List("Moscow", "New York"))
    personAct shouldEqual personExp
  }

  it should "deserialize case class to JSON (with Reads)" in {
    implicit val reads: Reads[Person1] = Json.reads[Person1]
    val json = """{"name":"John","age":30, "cities": ["Moscow", "New York"]}"""
    val jsValue = Json.parse(json)
    val personAct = jsValue.as[Person1]
    val personExp = Person1("John", 30, List("Moscow", "New York"))
    personAct shouldEqual personExp
  }

  case class Person1(name: String, age: Int, cities: List[String]) //Can't be in test body

  object Gender extends Enumeration {
    type Category = Value
    val MALE, FEMALE = Value
    implicit val format: Format[Gender.Value] = Json.formatEnum(this)
  }

  case class Person2(name: String, age: Int, gender: Gender.Category)

  it should "deserialize Enumeration (with OFormat)" in {
    implicit val format: OFormat[Person2] = Json.format[Person2] //Gender.format is detected automatically
    val json = """{"name":"John","age":30,"gender":"MALE"}"""
    val jsValue = Json.parse(json)
    val personAct = jsValue.as[Person2]
    val personExp = Person2("John", 30, Gender.MALE)
    personAct shouldEqual personExp
  }
}
