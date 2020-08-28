package json

import org.scalatest.{FlatSpec, Matchers}
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

}
