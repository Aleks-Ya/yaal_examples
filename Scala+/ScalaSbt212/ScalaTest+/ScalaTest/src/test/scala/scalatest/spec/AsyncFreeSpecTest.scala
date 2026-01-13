package scalatest.spec

import org.scalatest.freespec.AsyncFreeSpec
import org.scalatest.matchers.should.Matchers

import scala.concurrent.Future

class AsyncFreeSpecTest extends AsyncFreeSpec with Matchers {
  def addSoon(addends: Int*): Future[Int] = Future {
    addends.sum
  }

  "addSoon" - {
    "will eventually compute a sum of passed Ints" in {
      val futureSum: Future[Int] = addSoon(1, 2)
      futureSum map { sum => assert(sum == 3) }
    }
  }

  def addNow(addends: Int*): Int = addends.sum

  "addNow" - {
    "will immediately compute a sum of passed Ints" in {
      val sum: Int = addNow(1, 2)
      assert(sum == 3)
    }
  }

  "It" - {
    "recoverToSucceededIf" in {
      recoverToSucceededIf[IllegalArgumentException] {
        Future(throw new IllegalArgumentException("Error occurred"))
      }
    }
  }

  "It" - {
    "recoverToExceptionIf" in {
      recoverToExceptionIf[IllegalArgumentException] {
        Future(throw new IllegalArgumentException("Error occurred"))
      } map { ex =>
        ex should have message "Error occurred"
      }
    }
  }
}