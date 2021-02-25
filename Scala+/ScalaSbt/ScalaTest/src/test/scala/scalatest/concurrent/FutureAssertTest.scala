package scalatest.concurrent

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class FutureAssertTest extends AnyFlatSpec with Matchers {

  it should "assert successful Future result" in {
    val expValue = "a"
    val f = Future {
      expValue
    }
    ScalaFutures.whenReady(f) { s =>
      s shouldEqual expValue
    }
  }

  it should "assert failed Future result" in {
    val expException = new RuntimeException("failure")
    val f = Future[String] {
      throw expException
    }
    ScalaFutures.whenReady(f.failed) { e =>
      e shouldEqual expException
    }
  }

}