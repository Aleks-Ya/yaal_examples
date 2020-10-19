package tests

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scopt.OptionParser

/**
  * Parse a string with spaces.
  */
class ParseStringWithSpaces extends AnyFlatSpec with Matchers {

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
    configOpt should not be None
    configOpt.get.text shouldEqual exp
  }

}
