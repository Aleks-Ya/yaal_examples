package scala.concurrent

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ExecutorContextTest extends AnyFlatSpec with Matchers {
  "ExecutorContext" should "execute a Runnable" in {
    println(f"Main thread: ${Thread.currentThread().getName}")
    ExecutionContext.global.execute(() => println(f"Runnable thread: ${Thread.currentThread().getName}"))
  }
}
