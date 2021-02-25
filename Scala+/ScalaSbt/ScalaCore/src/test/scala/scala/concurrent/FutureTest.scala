package scala.concurrent

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}

class FutureTest extends AnyFlatSpec with Matchers {

  it should "invoke success or failure callback" in {
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
        print(f"Error thread: ${Thread.currentThread().getName}")
        print(f"Error: ${e.getMessage}")
    }
  }

  it should "invoke success callback only" in {
    val expText = "a"
    val f = Future[String] {
      expText
    }
    f foreach { actText => actText shouldEqual expText }
  }

  it should "wait for value of a Future" in {
    val expValue = "a"
    val f = Future[String] {
      expValue
    }
    val actValue = Await.result(f, Duration.Inf)
    f.isCompleted shouldBe true
    actValue shouldBe expValue
  }

  it should "successful Future" in {
    val expText = "abc"
    val f = Future.successful(expText)
    f.isCompleted shouldBe true
    ScalaFutures.whenReady(f) { actText =>
      actText shouldEqual expText
    }
  }

  it should "failed Future" in {
    val expException = new RuntimeException()
    val f = Future.failed(expException)
    f.isCompleted shouldBe true
    ScalaFutures.whenReady(f.failed) { e =>
      e shouldEqual expException
    }
  }

}
