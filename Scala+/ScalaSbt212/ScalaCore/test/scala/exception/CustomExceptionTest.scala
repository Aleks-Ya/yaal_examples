package scala.exception

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class CustomExceptionTest extends AnyFlatSpec with Matchers {

  it should "throw custom exception" in {
    class MyException extends RuntimeException

    assertThrows[MyException] {
      throw new MyException
    }
  }

  it should "throw custom exception with message" in {
    class MyException(message: String) extends RuntimeException(message)

    val message = "abc"
    val e = intercept[MyException] {
      throw new MyException(message)
    }
    e.getMessage shouldBe message
  }

  it should "throw custom exception with cause" in {
    class MyException(cause: Throwable) extends RuntimeException(cause)

    val cause = new NullPointerException
    val e = intercept[MyException] {
      throw new MyException(cause)
    }
    e.getCause shouldBe cause
  }

  it should "throw custom exception with message and cause" in {
    class MyException(message: String, cause: Throwable) extends RuntimeException(message, cause)

    val message = "abc"
    val cause = new NullPointerException
    val e = intercept[MyException] {
      throw new MyException(message, cause)
    }
    e.getMessage shouldBe message
    e.getCause shouldBe cause
  }

  it should "throw custom exception with custom parameter" in {
    class MyException(message: String, cause: Throwable) extends RuntimeException(message, cause) {
      def this(number: Int) = this(s"The number: $number", null)
    }

    val number = 7
    val e = intercept[MyException] {
      throw new MyException(number)
    }
    e.getMessage shouldBe s"The number: $number"
    e.getCause shouldBe null
  }

}
