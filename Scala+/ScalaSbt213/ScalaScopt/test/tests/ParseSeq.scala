package tests

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scopt.OptionParser

/**
  * Parse a Seq.
  */
class ParseSeq extends AnyFlatSpec with Matchers {

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
    configOpt should not be None
    configOpt.get.lines shouldEqual exp
  }
}
