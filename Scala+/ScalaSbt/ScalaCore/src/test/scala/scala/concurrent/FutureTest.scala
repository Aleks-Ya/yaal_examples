package scala.concurrent

import org.scalatest.concurrent.PatienceConfiguration.Timeout
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.time.{Seconds, Span}

import scala.collection.mutable
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}

class FutureTest extends AnyFlatSpec with Matchers {

  it should "invoke success or failure callback" in {
    println(f"Main thread: ${Thread.currentThread().getName}")
    val f = Future[String] {
      println(f"Future thread: ${Thread.currentThread().getName}")
      Thread.sleep(3000)
      print("Feature done")
      "a"
    }
    f onComplete {
      case Success(str) =>
        println(f"Success thread: ${Thread.currentThread().getName}")
        println(f"Success: $str")
      case Failure(e) =>
        println(f"Error thread: ${Thread.currentThread().getName}")
        println(f"Error: ${e.getMessage}")
    }
    ScalaFutures.whenReady(f, timeout = Timeout(Span(5, Seconds))) { _ => println("Ready") }
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

  "Future" should "has no return value" in {
    val sb = new mutable.StringBuilder()
    val f = Future[Unit] {
      sb.append("ok")
    }
    ScalaFutures.whenReady(f) { _ =>
      sb.toString() shouldEqual "ok"
    }
  }

}
