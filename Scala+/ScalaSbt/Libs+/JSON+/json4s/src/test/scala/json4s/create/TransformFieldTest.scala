package json4s.create

import org.json4s.JString
import org.json4s.JsonAST.JField
import org.json4s.native.JsonMethods
import org.json4s.native.JsonMethods._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class TransformFieldTest extends AnyFlatSpec with Matchers {
  it should "modify all fields in an Object" in {
    val jsonObject = JsonMethods.parse("""{"name":"John","age":30}""")
    val updatedObject = jsonObject transformField { case (n, v) => n.toUpperCase -> v }
    val jsonString = compact(render(updatedObject))
    jsonString shouldEqual """{"NAME":"John","AGE":30}"""
  }

  it should "modify one field in an Object" in {
    val jsonObject = JsonMethods.parse("""{"name":"John","age":30}""")
    val updatedObject = jsonObject transformField { case ("name", JString("John")) => "person" -> JString("Mary") }
    val jsonString = compact(render(updatedObject))
    jsonString shouldEqual """{"person":"Mary","age":30}"""
  }

  it should "modify field by name in an Object" in {
    val jsonObject = JsonMethods.parse("""{"name":"John","age":30}""")
    val updatedObject = jsonObject transformField { case ("name", v) => "person" -> v }
    val jsonString = compact(render(updatedObject))
    jsonString shouldEqual """{"person":"John","age":30}"""
  }

}