package scala.option

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.util.Try

class TryTest extends AnyFlatSpec with Matchers {

  it should "try" in {
    Try("123".toInt).toOption should not be empty
    Try("NaN".toInt).toOption shouldBe empty
  }
}