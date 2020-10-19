package tests

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scopt.OptionParser

/**
  * Parse URL.
  */
class ParseUrl extends AnyFlatSpec with Matchers {

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
    configOpt should not be None
    configOpt.get.url shouldEqual urlValueExp
  }

}
