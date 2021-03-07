package json

import net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import play.api.libs.functional.syntax.{toFunctionalBuilderOps, unlift}
import play.api.libs.json._

class SerializeWrites extends AnyFlatSpec with Matchers {

  case class Person1(name: String, age: Int, cities: List[String]) //Can't be in test body

  it should "serialize a case class to JSON with generated Writes (automated mapping)" in {
    implicit val writes: OWrites[Person1] = Json.writes[Person1]
    val person = Person1("John", 30, List("Moscow", "New York"))
    val jsonValue = Json.toJson(person)
    val jsonStr = Json.stringify(jsonValue)
    assertJsonEquals("""{"name":"John","age":30, "cities": ["Moscow", "New York"]}""", jsonStr)
  }

  it should "serialize a map to JSON" in {
    val map = Map("a" -> 1, "b" -> 2)
    val jsonValue = Json.toJson(map)
    val jsonStr = Json.stringify(jsonValue)
    assertJsonEquals("""{"a":1,"b":2}""", jsonStr)
  }

  it should "serialize a case class to JSON with custom Writes (manual mapping)" in {
    case class Person1(name: String, age: Int)
    implicit val personWrites: Writes[Person1] = (resident: Person1) => Json.obj(
      "name" -> resident.name,
      "age" -> resident.age
    )
    val person = Person1("John", 30)
    val jsonValue = Json.toJson(person)
    val jsonStr = Json.stringify(jsonValue)
    assertJsonEquals("""{"name":"John","age":30}""", jsonStr)
  }

  it should "serialize a case class to JSON with custom Writes (manual mapping, combinator pattern)" in {
    case class Person2(name: String, age: Int)
    implicit val personWrites: Writes[Person2] = (
      (JsPath \ "name").write[String] and
        (JsPath \ "age").write[Int]
      ) (unlift(Person2.unapply))
    val person = Person2("John", 30)
    val jsonValue = Json.toJson(person)
    val jsonStr = Json.stringify(jsonValue)
    assertJsonEquals("""{"name":"John","age":30}""", jsonStr)
  }

}
