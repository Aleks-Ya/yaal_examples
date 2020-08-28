package json

import org.scalatest.{FlatSpec, Matchers}
import play.api.libs.json.{JsError, JsResult, JsSuccess, Json}

/**
 * JSON manipulation with play.libs.Json.
 */
class PlayJsonSpec extends FlatSpec with Matchers {

  it should "serialize map to JSON" in {
    val map = Map("a" -> 1, "b" -> 2)
    val jsonValue = Json.toJson(map)
    val jsonStr = Json.stringify(jsonValue)
    jsonStr shouldEqual """{"a":1,"b":2}"""
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
