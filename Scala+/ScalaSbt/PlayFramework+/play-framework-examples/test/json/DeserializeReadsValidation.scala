package json

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import play.api.libs.json.JsValue.jsValueToJsLookup
import play.api.libs.json._

class DeserializeReadsValidation extends AnyFlatSpec with Matchers {

  it should "validate a string JSON property" in {
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
