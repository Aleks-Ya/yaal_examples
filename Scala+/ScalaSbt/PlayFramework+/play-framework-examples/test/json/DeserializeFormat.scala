package json

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import play.api.libs.json._

class DeserializeFormat extends AnyFlatSpec with Matchers {

  it should "deserialize JSON to a case class (automated mapping)" in {
    implicit val format: OFormat[Person1] = Json.format[Person1]
    val json = """{"name":"John","age":30,"gender":"MALE", "cities": ["Moscow", "New York"]}"""
    val jsValue = Json.parse(json)
    val personAct = jsValue.as[Person1]
    val personExp = Person1("John", 30, Gender.MALE, List("Moscow", "New York"))
    personAct shouldEqual personExp
  }

  case class Person1(name: String, age: Int, gender: Gender.Category, cities: List[String]) //Can't be in test body

  object Gender extends Enumeration {
    type Category = Value
    val MALE, FEMALE = Value
    implicit val format: Format[Gender.Value] = Json.formatEnum(this)
  }

}
