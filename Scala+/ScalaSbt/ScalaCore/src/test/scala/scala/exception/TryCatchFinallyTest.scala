package scala.exception

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.io.IOException

class TryCatchFinallyTest extends AnyFlatSpec with Matchers {

  it should "catch exception" in {
    def throwException = throw new IllegalArgumentException

    var exception: Exception = null
    var finallyResult = false
    try {
      throwException
    } catch {
      case e: IllegalArgumentException => exception = e;
      case e: IllegalStateException => exception = e;
      case e: IOException => exception = e;
    } finally {
      finallyResult = true
    }
    exception shouldBe a[IllegalArgumentException]
    finallyResult shouldBe true
  }
}
