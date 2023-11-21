package json4s.create

import org.json4s.native.JsonMethods
import org.json4s.native.JsonMethods._
import org.json4s.{JInt, JString}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class TransformValueTest extends AnyFlatSpec with Matchers {
  it should "modify values in an Object" in {
    val jsonObject = JsonMethods.parse("""{"name":"John","age":30}""")
    val updatedObject = jsonObject transform {
      case JString(v) => JString(v.toUpperCase)
      case JInt(v) => JInt(v.toInt * 2)
    }
    val jsonString = compact(render(updatedObject))
    jsonString shouldEqual """{"name":"JOHN","age":60}"""
  }
}