package tests

import org.scalatest.{FlatSpec, Matchers}
import scopt.OptionParser

class StringOpts extends FlatSpec with Matchers {

  it should "process String arguments" in {
    case class Config(personName: String = "",
                      personTitle: String = "Mr")

    val programName = getClass.getSimpleName
    val parser = new OptionParser[Config](programName) {
      opt[String]("name").required().action({
        (value, config) => config.copy(personName = value)
      })
      opt[String]("title").action({
        (value, config) => config.copy(personTitle = value)
      })
    }
    val nameValueExp = "John"
    val titleValueExp = "Sir"

    val args = List("--name", nameValueExp, "--title", titleValueExp)

    val configOpt = parser.parse(args, Config())
    configOpt shouldBe defined

    val config = configOpt.get
    config.personName shouldEqual nameValueExp
    config.personTitle shouldEqual titleValueExp
  }

}
