package scala.exception

import java.io.IOException

import org.scalatest.{FlatSpec, Matchers}

class TryCatchFinallyTest extends FlatSpec with Matchers {

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
