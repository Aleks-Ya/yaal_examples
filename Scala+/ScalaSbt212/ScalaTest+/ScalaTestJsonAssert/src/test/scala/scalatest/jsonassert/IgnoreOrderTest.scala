package scalatest.jsonassert

import com.stephenn.scalatest.jsonassert.JsonMatchers
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class IgnoreOrderTest extends AnyFlatSpec with Matchers with JsonMatchers {

  it should "ignore fields order" in {
    val json1 = """{"name":"John","age":30}"""
    val json2 = """{"age":30,"name":"John"}"""
    json1 should matchJson(json2)
  }

  it should "do not ignore list elements order" in {
    val json1 = """{"fruits":["apple","orange"]}"""
    val json2 = """{"fruits":["orange","apple"]}"""
    json1 shouldNot matchJson(json2)
  }
}