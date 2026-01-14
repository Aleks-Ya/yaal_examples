package tests

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scopt.OptionParser

/**
  * Parse a Map.
  */
class ParseMap extends AnyFlatSpec with Matchers {

  private case class Config(entries: Map[String, Int] = null)

  it should "parse a map" in {
    check(Array("""--lines=ab=1,cd=2,ef=3"""), Map("ab" -> 1, "cd" -> 2, "ef" -> 3))
  }

  def check(args: Array[String], exp: Any): Unit = {
    val programName = getClass.getSimpleName
    val parser = new OptionParser[Config](programName) {
      opt[Map[String, Int]]("lines").action({
        (value, config) => config.copy(entries = value)
      })
    }
    val configOpt = parser.parse(args, Config())
    configOpt should not be None
    configOpt.get.entries shouldEqual exp
  }
}
