package option

import org.scalatest.{FlatSpec, Matchers}

import scala.util.Try

class TryTest extends FlatSpec with Matchers {

  it should "try" in {
    Try("123".toInt).toOption should not be empty
    Try("NaN".toInt).toOption shouldBe empty
  }
}