package json

import net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import play.api.libs.json._

class OptionTest extends AnyFlatSpec with Matchers {

  it should "not write None as null (default)" in {
    implicit val writes: OWrites[Person] = Json.writes[Person]
    val json1 = Json.stringify(Json.toJson(Person(None, 30)))
    assertJsonEquals("""{"age":30}""", json1)
    val json2 = Json.stringify(Json.toJson(Person(Some("John"), 30)))
    assertJsonEquals("""{"name":"John","age":30}""", json2)
  }

  ignore should "write None as null (not working)" in {
    implicit val writes: OWrites[Person] = Json.writes[Person]
    val config = JsonConfiguration(optionHandlers = OptionHandlers.WritesNull)
    val json = Json.configured(config)
    val json2 = json.stringify(json.toJson(Person(None, 30)))
    assertJsonEquals("""{"name":null,"age":30}""", json2)
  }

  case class Person(name: Option[String], age: Int) //Can't be in test body

}
