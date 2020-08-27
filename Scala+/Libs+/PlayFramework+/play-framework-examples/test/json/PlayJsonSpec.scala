package json

import org.scalatest.{FlatSpec, Matchers}
import play.api.libs.json.Json

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

  it should "deserialize JSON to object" in {
//    case class Person(name: String, age: Int)
//    val person = Person("John", 30)
//    val jsonStr = """{"name": "John", "age": 30}"""
//    Json.parse(jsonStr)
//    val jsonValue = Json.toJson(map)
//    Json.toJsObject(jsonStr)
//    val jsonStrAct = Json.stringify(jsonValue)
//    jsonStrAct shouldEqual """{"a":1,"b":2}"""
  }
}
