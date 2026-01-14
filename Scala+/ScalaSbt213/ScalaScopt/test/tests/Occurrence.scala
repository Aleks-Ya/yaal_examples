package tests

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scopt.OptionParser

/**
 * Several options with the same name.
 */
class Occurrence extends AnyFlatSpec with Matchers {

  it should "process String arguments" in {
    case class Config(names: Seq[String] = Seq())

    val programName = getClass.getSimpleName
    val parser = new OptionParser[Config](programName) {
      opt[String]("name").maxOccurs(2).required().action({
        (value, config) => {
          val names = config.names ++ Seq(value)
          config.copy(names = names)
        }
      })
    }
    val expName1 = "John"
    val expName2 = "Sir"

    val args = Array("--name", expName1, "--name", expName2)

    val configOpt = parser.parse(args, Config())
    configOpt should not be None

    val config = configOpt.get
    config.names should contain allOf(expName1, expName2)
  }

}
