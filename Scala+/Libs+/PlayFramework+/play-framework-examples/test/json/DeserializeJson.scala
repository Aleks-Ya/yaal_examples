package json

import org.scalatest.{FlatSpec, Matchers}
import play.api.libs.functional.syntax.toFunctionalBuilderOps
import play.api.libs.json._

/**
 * Deserialize and validate JSON with play.libs.Json.
 */
class DeserializeJson extends FlatSpec with Matchers {

  it should "deserialize JSON to object" in {
    case class Person(name: String, age: Int, position: Position)
    case class Position(id: Long, title: String)

    implicit val positionReads: Reads[Position] = (
      (__ \ "id").read[Long] and
        (__ \ "title").read[String]
      ) (Position.apply _)

    implicit val personReads: Reads[Person] = (
      (__ \ "name").read[String] and
        (__ \ "age").read[Int] and
        (__ \ "position").read[Position]
      ) (Person.apply _)

    val name = "John"
    val age = 30
    val positionId = 1L
    val positionTitle = "Boss"
    val jsValue = Json.parse(s"""{ "name":"$name", "age":$age, "position": {"id":$positionId, "title":"$positionTitle"} }""")
    val personResult = jsValue.validate[Person]
    val personAct = personResult.get
    personAct shouldEqual Person(name, age, Position(positionId, positionTitle))
  }

  it should "validate string JSON property" in {
    val json = Json.parse("""{ "name": "John" }""")

    val nameResult: JsResult[String] = (json \ "name").validate[String]

    // Pattern matching
    nameResult match {
      case JsSuccess(name, _) => println(s"Name: $name")
      case e: JsError => println(s"Errors: ${JsError.toJson(e)}")
    }

    // Fallback value
    val nameOrFallback = nameResult.getOrElse("Undefined")
    nameOrFallback shouldEqual "John"

    // map
    val nameUpperResult: JsResult[String] = nameResult.map(_.toUpperCase)
    nameUpperResult.get shouldEqual "JOHN"

    // fold
    val nameOption: Option[String] = nameResult.fold(
      invalid = { fieldErrors =>
        fieldErrors.foreach { x =>
          println(s"field: ${x._1}, errors: ${x._2}")
        }
        Option.empty[String]
      },
      valid = Some(_)
    )
    nameOption.isDefined shouldBe true
    nameOption.get shouldEqual "John"
  }

  it should "validate number JSON property" in {
    val json = Json.parse("""{ "year": 2020 }""")
    val yearResult: JsResult[Int] = (json \ "year").validate[Int]

    yearResult match {
      case JsSuccess(year, _) => println(s"Year: $year")
      case e: JsError => println(s"Errors: ${JsError.toJson(e)}")
    }

    val yearOrFallback = yearResult.getOrElse("1920")
    yearOrFallback shouldEqual 2020

    val nameOption: Option[Int] = yearResult.fold(
      invalid = { fieldErrors =>
        fieldErrors.foreach { x =>
          println(s"field: ${x._1}, errors: ${x._2}")
        }
        Option.empty[Int]
      },
      valid = Some(_)
    )
    nameOption.isDefined shouldBe true
    nameOption.get shouldEqual 2020
  }
}