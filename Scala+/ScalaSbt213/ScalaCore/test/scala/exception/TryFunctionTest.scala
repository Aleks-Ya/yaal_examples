package scala.exception

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.util.Try

class TryFunctionTest extends AnyFlatSpec with Matchers {

  it should "try to option (with exception)" in {
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
    val either = Try(throwException).toEither
    either.isLeft shouldBe true
    either.isRight shouldBe false
  }

  it should "check was exception of not" in {
    Try(throwException).isFailure shouldBe true
    Try(throwException).isSuccess shouldBe false
  }

  it should "get or else" in {
    val result = Try(throwException).getOrElse("abc")
    result shouldEqual "abc"
  }

  it should "return value on given exception" in {
    val result = Try(throwException)
      .recover {
        case _: IllegalArgumentException => "problem"
      }
      .getOrElse("ok")
    result shouldEqual "problem"
  }

  private def throwException: String = throw new IllegalArgumentException
}
