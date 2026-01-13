package json4s.create

import org.json4s.JString
import org.json4s.JsonAST.JObject
import org.json4s.native.JsonMethods
import org.json4s.native.JsonMethods._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class AddFieldTest extends AnyFlatSpec with Matchers {
  it should "add new field into an existing JSON object" in {
    val jsonObject = JsonMethods.parse("""{"name":"John","age":30}""")
    val newField = "city" -> JString("London")
    val updatedJson = jsonObject merge JObject(newField)
    val jsonString = compact(render(updatedJson))
    jsonString shouldEqual """{"name":"John","age":30,"city":"London"}"""
  }

  it should "add another JSON object as a string field" in {
    val mainJsonObject = JsonMethods.parse("""{"name":"John","age":30}""")
    val locationJsonObjectStr = """{"city":"London","year":2023}"""
    val locationField = "location" -> JString(locationJsonObjectStr)
    val updatedJson = mainJsonObject merge JObject(locationField)
    val jsonString = compact(render(updatedJson))
    jsonString shouldEqual """{"name":"John","age":30,"location":"{\"city\":\"London\",\"year\":2023}"}"""
  }
}