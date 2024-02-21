package json

import net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import play.api.libs.json.JsonConfiguration.Aux
import play.api.libs.json._

class ConfiguredJson extends AnyFlatSpec with Matchers {

  it should "apply custom JsonConfiguration to Json object" in {
    object CustomNamingStrategy extends JsonNaming {
      private val propertyToColumnMap = Map("name" -> "name2")

      override def apply(property: String): String = propertyToColumnMap.getOrElse(property, property)
    }
    val config: Aux[Json.MacroOptions] = JsonConfiguration(CustomNamingStrategy)

    val json = Json.configured(config)

    implicit val format: OFormat[Person] = json.format[Person]
    val expJson = """{"name2":"John","age":30}"""
    val person = json.parse(expJson).as[Person]
    val actJson = json.stringify(Json.toJson(person))
    assertJsonEquals(expJson, actJson)
  }

  case class Person(name: String, age: Int) //Can't be in test body

}
