package scala.clazz.pimp

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.clazz.pimp.BigDecimalExtensions.BigDecimalExtended

/**
 * Add a custom method to read-only third-party library.
 */
class PimpMyLibraryTest extends AnyFlatSpec with Matchers {
  it should "invoke a custom method" in {
    val thirdPartyObject = BigDecimal(1)
    val result = thirdPartyObject.luckyNumber()
    result shouldEqual 777
  }
}
