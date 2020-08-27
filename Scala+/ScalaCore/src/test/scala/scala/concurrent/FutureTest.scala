package scala.concurrent

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

class FutureTest extends AnyFlatSpec with Matchers {

  it should "invoke success callback" in {
    print(f"Main thread: ${Thread.currentThread().getName}")
    val f = Future[String] {
      print(f"Future thread: ${Thread.currentThread().getName}")
      Thread.sleep(5000)
      "a"
    }

    f onComplete {
      case Success(str) =>
        print(f"Success thread: ${Thread.currentThread().getName}")
        print(f"Success: $str")
      case Failure(e) =>
        print(f"Success thread: ${Thread.currentThread().getName}")
        print(f"Error: ${e.getMessage}")
    }
  }

}
