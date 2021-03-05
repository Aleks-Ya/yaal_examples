package config

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ConfigSpec extends AnyFlatSpec with Matchers {
  it should "get property from config file" in {
    ConfigReloadable.magicNumber shouldEqual 7
  }
}
