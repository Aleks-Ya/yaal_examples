package tests

import org.scalatest.{FlatSpec, Matchers}
import scopt.OptionParser

/**
  * Parse a string with spaces.
  */
class ParseStringWithSpaces extends FlatSpec with Matchers {

  private case class Config(text: String = "")

  it should "parse string with spaces" in {
    check(Array("""--text="abc 123""""), "abc 123")
    check(Array("""--text="""""), "")
    check(Array("""--text="""), "")
  }

  def check(args: Array[String], exp: String): Unit = {
    val programName = getClass.getSimpleName
    val parser = new OptionParser[Config](programName) {
      opt[String]("text").action({
        (value, config) => {
          val trimQuotes = value.replaceAll("^\"|\"$", "")
          config.copy(text = trimQuotes)
        }
      })
    }
    val configOpt = parser.parse(args, Config())
    configOpt shouldBe defined
    configOpt.get.text shouldEqual exp
  }

}
