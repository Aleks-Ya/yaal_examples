package scala.exception

import org.scalatest.{FlatSpec, Matchers}

import scala.util.Try

class TryFunctionTest extends FlatSpec with Matchers {

  it should "try to option (with exception)" in {
    def throwException = throw new IllegalArgumentException

    val option = Try(throwException).toOption
    option shouldBe None
    option.getOrElse("if none") shouldEqual "if none"
  }

  it should "try to option (no exception)" in {
    def notThrowException = "abc"

    val option = Try(notThrowException).toOption
    option.get shouldEqual "abc"
  }

  it should "try to either (with exception)" in {
    def throwException = throw new IllegalArgumentException

    val either = Try(throwException).toEither
    either.isLeft shouldBe true
    either.isRight shouldBe false
  }
}
