package scalatest.concurrent

import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.concurrent.Future

class AsyncFlatSpecTest extends AsyncFlatSpec with Matchers {

  it should "assert successful Future result" in {
    val expValue = "a"
    val f = Future {
      expValue
    }
    f map { actValue => actValue shouldEqual expValue }
  }

}