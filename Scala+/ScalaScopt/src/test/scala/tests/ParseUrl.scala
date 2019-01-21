package tests

import org.scalatest.{FlatSpec, Matchers}
import scopt.OptionParser

/**
  * Parse URL.
  */
class ParseUrl extends FlatSpec with Matchers {

  private case class Config(url: String = "")

  private val urlValueExp = "jdbc:postgresql://10.66.131.184:5432/postgres"
  private val urlName = "site-url"

  it should "parse URL" in {
    check(Array(s"--$urlName=jdbc:postgresql://10.66.131.184:5432/postgres"))
  }

  def check(args: Array[String]): Unit = {
    val programName = getClass.getSimpleName
    val parser = new OptionParser[Config](programName) {
      opt[String](urlName).action({
        (value, config) => config.copy(url = value)
      })
    }
    val configOpt = parser.parse(args, Config())
    configOpt shouldBe defined
    configOpt.get.url shouldEqual urlValueExp
  }

}
