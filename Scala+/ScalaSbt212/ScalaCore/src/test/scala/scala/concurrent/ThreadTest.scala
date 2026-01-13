package scala.concurrent

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ThreadTest extends AnyFlatSpec with Matchers {
  it should "run a Thread as a daemon" in {
    println(f"Main thread: ${Thread.currentThread().getName}")
    val t = new Thread("Thread1") {
      override def run(): Unit = {
        println(f"Another thread: ${Thread.currentThread().getName}")
      }
    }
    t.setDaemon(true)
    t.start()
    Thread.sleep(1000)
  }
}
