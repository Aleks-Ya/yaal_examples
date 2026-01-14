package scala.string.regex

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class RegexGroupTest extends AnyFlatSpec with Matchers {

  it should "get one group" in {
    val text = "Hello John now!"
    val pattern = """Hello\s(\w+)\snow!""".r
    val matches = pattern.findFirstMatchIn(text)
    val name = matches.get.group(1)
    name shouldEqual "John"
  }

  it should "get several groups" in {
    val titleLine = "2) Is employed: CATEGORICAL\r"
    val patternTitle = """^(\d+)\) (\w[\w\s]*): (\w+)$""".r
    val titleMatches = patternTitle.findFirstMatchIn(titleLine).get
    val id = titleMatches.group(1).toInt
    val title = titleMatches.group(2)
    val category = titleMatches.group(3)
    id shouldBe 2
    title shouldBe "Is employed"
    category shouldBe "CATEGORICAL"
  }

}
