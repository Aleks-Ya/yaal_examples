package scalatest.jsonassert

import com.stephenn.scalatest.jsonassert.JsonMatchers
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

/**
 * Source: https://github.com/stephennancekivell/scalatest-json
 */
class ExampleFromDocsTest extends AnyFunSpec with Matchers with JsonMatchers {

  describe("JsonMatchers usage") {
    it("should pass matching json with different spacing and order") {
      val input =
        """
          |{
          | "some": "valid json",
          | "plus": ["json", "content"]
          |}
      """.stripMargin

      val expected =
        """
          |{
          | "plus": ["json", "content"],
          |     "some":   "valid json"
          |}
                  """.stripMargin

      input should matchJson(expected)
    }

    it("should fail on slightly different json explaining why") {
      val input =
        """
          |{
          | "someField": "valid json"
          |}
                  """.stripMargin

      val expected =
        """
          |{
          | "someField": "different json"
          |}
                     """.stripMargin

      input should matchJson(expected)
    }
  }
}