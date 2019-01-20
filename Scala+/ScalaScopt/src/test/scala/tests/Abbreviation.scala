package tests

import org.scalatest.{FlatSpec, Matchers}
import scopt.OptionParser

class Abbreviation extends FlatSpec with Matchers {

  it should "use an abbreviation for an option" in {
    case class Config(personName: String = "")

    val programName = getClass.getSimpleName
    val parser = new OptionParser[Config](programName) {
      opt[String]("name").abbr("n").action({
        (value, config) => config.copy(personName = value)
      })
    }
    val nameValueExp = "John"

    val argsFull = List("--name", nameValueExp)
    val configOptFull = parser.parse(argsFull, Config())
    configOptFull.get.personName shouldEqual nameValueExp

    val argsAbbr = List("-n", nameValueExp)
    val configOptAbbr = parser.parse(argsAbbr, Config())
    configOptAbbr.get.personName shouldEqual nameValueExp
  }

}
