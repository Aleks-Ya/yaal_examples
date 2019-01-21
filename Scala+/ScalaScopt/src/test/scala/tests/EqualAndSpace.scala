package tests

import org.scalatest.{FlatSpec, Matchers}
import scopt.OptionParser

/**
  * Use "=" and space as a value delimiter.
  */
class EqualAndSpace extends FlatSpec with Matchers {

  private case class Config(personName: String = "")

  private val nameValueExp = "John"

  it should "parse with = and space" in {
    check(Array(s"--name=$nameValueExp"))
    check(Array("--name", nameValueExp))
    check(Array(s"-n=$nameValueExp"))
    check(Array("-n", nameValueExp))
  }

  def check(args: Array[String]): Unit = {
    val programName = getClass.getSimpleName
    val parser = new OptionParser[Config](programName) {
      opt[String]("name").abbr("n").action({
        (value, config) => config.copy(personName = value)
      })
    }
    val configOpt = parser.parse(args, Config())
    configOpt shouldBe defined
    configOpt.get.personName shouldEqual nameValueExp
  }

}
