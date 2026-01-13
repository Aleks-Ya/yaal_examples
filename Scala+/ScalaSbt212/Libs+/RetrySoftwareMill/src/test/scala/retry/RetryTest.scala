package retry

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class RetryTest extends AnyFlatSpec with Matchers {

  it should "backoff" in {
    implicit val success: Success[Int] = retry.Success[Int](isPrime)
  }

  private def isPrime(number: Int): Boolean = {
    var prime = true
    for (i <- 2 until number if number % i == 0) {
      prime = false
    }
    prime
  }
}