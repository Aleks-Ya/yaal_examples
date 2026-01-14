package scala.clazz.pimp

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.clazz.pimp.IntExtensions1.IntExtended1
import scala.clazz.pimp.IntExtensions2.IntExtended2

/**
 * Add custom methods from different classes to read-only third-party library.
 */
class MultipleExtensionsTest extends AnyFlatSpec with Matchers {
  it should "invoke custom methods from different extensions" in {
    val thirdPartyObject = 2
    thirdPartyObject.even() shouldBe true
    thirdPartyObject.odd() shouldBe false
  }
}
