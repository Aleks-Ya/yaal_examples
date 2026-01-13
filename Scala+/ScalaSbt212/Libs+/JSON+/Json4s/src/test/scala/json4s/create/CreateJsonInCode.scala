package json4s.create

import org.json4s.JsonAST.{JField, JObject}
import org.json4s.JsonDSL._
import org.json4s.native.JsonMethods._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class CreateJsonInCode extends AnyFlatSpec with Matchers {

  it should "construct a JSON object" in {
    val jsonObject = ("name" -> "John Doe") ~
      ("age" -> 30) ~
      ("address" -> ("street" -> "123 Main St") ~ ("city" -> "Anytown"))
    val jsonString = compact(render(jsonObject))
    jsonString shouldEqual """{"name":"John Doe","age":30,"address":{"street":"123 Main St","city":"Anytown"}}"""
  }

  it should "construct a JSON object 2" in {
    val jsonObject = JObject(
      JField("name", "John"),
      JField("age", 30)
    )
    val jsonString = compact(render(jsonObject))
    jsonString shouldEqual """{"name":"John","age":30}"""
  }

}