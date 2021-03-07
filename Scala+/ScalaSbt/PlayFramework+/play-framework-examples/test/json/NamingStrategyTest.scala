package json

import net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import play.api.libs.json.JsonConfiguration.Aux
import play.api.libs.json._

class NamingStrategyTest extends AnyFlatSpec with Matchers {

  it should "use custom field names during serializing and deserializing" in {
    object CustomNamingStrategy extends JsonNaming {
      private val propertyToColumnMap = Map("name" -> "name2")

      override def apply(property: String): String = propertyToColumnMap.getOrElse(property, property)
    }
    implicit val config: Aux[Json.MacroOptions] = JsonConfiguration(CustomNamingStrategy)
    implicit val format: OFormat[Person] = Json.format[Person]

    val json1 = """{"name2":"John","age":30}"""
    val actPerson = Json.parse(json1).as[Person]
    val expPerson = Person("John", 30)
    actPerson shouldEqual expPerson

    val json2 = Json.stringify(Json.toJson(expPerson))
    assertJsonEquals(json1, json2)
  }

  case class Person(name: String, age: Int) //Can't be in test body

}
