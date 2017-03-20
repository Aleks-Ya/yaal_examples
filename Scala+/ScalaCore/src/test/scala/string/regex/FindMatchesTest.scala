package string.regex

import org.scalatest.{FlatSpec, Matchers}

class FindMatchesTest extends FlatSpec with Matchers {

  it should "find match" in {
    val text = "Hello John now!"
    val pattern = """Hello\s(\w+)\snow!""".r
    val matches = pattern.findFirstMatchIn(text)
    val name = matches.get.group(1)
    name shouldEqual "John"
  }

}
