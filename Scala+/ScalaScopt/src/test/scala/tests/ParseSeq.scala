package tests

import org.scalatest.{FlatSpec, Matchers}
import scopt.OptionParser

/**
  * Parse a Seq.
  */
class ParseSeq extends FlatSpec with Matchers {

  private case class Config(lines: Seq[String] = null)

  it should "parse sequence of strings" in {
    check(Array("""--lines=ab,cd,ef"""), Seq("ab", "cd", "ef"))
  }

  def check(args: Array[String], exp: Any): Unit = {
    val programName = getClass.getSimpleName
    val parser = new OptionParser[Config](programName) {
      opt[Seq[String]]("lines").action({
        (value, config) => config.copy(lines = value)
      })
    }
    val configOpt = parser.parse(args, Config())
    configOpt shouldBe defined
    configOpt.get.lines shouldEqual exp
  }
}
