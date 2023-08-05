package scala.properties

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.util.Properties

class PropertiesTest extends AnyFlatSpec with Matchers {

  it should "get Scala version" in {
    val str = Properties.versionString
    val numberStr = Properties.versionNumberString
    println(s"versionString: '$str'")
    println(s"versionNumberString: '$numberStr'")
  }

}
