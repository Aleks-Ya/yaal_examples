package json

import org.scalatest.{FlatSpec, Matchers}
import play.api.libs.functional.syntax.{toFunctionalBuilderOps, unlift}
import play.api.libs.json._

/**
 * Serialize JSON with play.libs.Json.
 */
class SerializeJson extends FlatSpec with Matchers {

  it should "serialize map to JSON" in {
    val map = Map("a" -> 1, "b" -> 2)
    val jsonValue = Json.toJson(map)
    val jsonStr = Json.stringify(jsonValue)
    jsonStr shouldEqual """{"a":1,"b":2}"""
  }

  it should "serialize case class to JSON (implicit Writes)" in {
    case class Person1(name: String, age: Int)
    implicit val personWrites: Writes[Person1] = (resident: Person1) => Json.obj(
      "name" -> resident.name,
      "age" -> resident.age
    )
    val person = Person1("John", 30)
    val jsonValue = Json.toJson(person)
    val jsonStr = Json.stringify(jsonValue)
    jsonStr shouldEqual """{"name":"John","age":30}"""
  }

  it should "serialize case class to JSON (implicit Writes - combinator pattern)" in {
    case class Person2(name: String, age: Int)
    implicit val personWrites: Writes[Person2] = (
      (JsPath \ "name").write[String] and
        (JsPath \ "age").write[Int]
      ) (unlift(Person2.unapply))
    val person = Person2("John", 30)
    val jsonValue = Json.toJson(person)
    val jsonStr = Json.stringify(jsonValue)
    jsonStr shouldEqual """{"name":"John","age":30}"""
  }

  case class Person3(name: String, age: Int) //Can't be in test body

  it should "serialize case class to JSON (auto format generation)" in {
    implicit val personFormat: OFormat[Person3] = Json.format[Person3]
    val person = Person3("John", 30)
    val jsonValue = Json.toJson(person)
    val jsonStr = Json.stringify(jsonValue)
    jsonStr shouldEqual """{"name":"John","age":30}"""
  }

}