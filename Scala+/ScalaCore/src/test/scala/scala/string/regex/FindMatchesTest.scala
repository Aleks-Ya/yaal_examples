package scala.string.regex

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class FindMatchesTest extends AnyFlatSpec with Matchers {

  it should "find match" in {
    val text = "Hello John now!"
    val pattern = """Hello\s(\w+)\snow!""".r
    val matches = pattern.findFirstMatchIn(text)
    val name = matches.get.group(1)
    name shouldEqual "John"
  }

  it should "find match 2" in {
    val titleLine = "2) Is employed: CATEGORICAL\r"
    val patternTitle = """^(\d+)\) (\w[\w\s]*): (\w+)$""".r
    val titleMathches = patternTitle.findFirstMatchIn(titleLine).get
    val id = titleMathches.group(1).toInt
    val title = titleMathches.group(2)
    val category = titleMathches.group(3)
  }

}
