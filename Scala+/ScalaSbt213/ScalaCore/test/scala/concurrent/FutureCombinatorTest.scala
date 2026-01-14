package scala.concurrent

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.concurrent.ExecutionContext.Implicits.global

class FutureCombinatorTest extends AnyFlatSpec with Matchers {

  it should "use map Future combinator" in {
    val f = Future[String] {
      "a"
    } map { text => text.toUpperCase } map { text => text * 3 }
    ScalaFutures.whenReady(f) { actText =>
      actText shouldEqual "AAA"
    }
  }


}
