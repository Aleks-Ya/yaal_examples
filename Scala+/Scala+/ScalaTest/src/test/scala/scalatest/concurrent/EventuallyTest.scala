package scalatest.concurrent

import org.scalatest.concurrent.Eventually
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.time.{Seconds, Span}

class EventuallyTest extends AnyFlatSpec with Matchers with Eventually {

  it should "finished successful" in {
    val xs = 1 to 125
    val it = xs.iterator
    eventually {
      it.next should be(3)
    }
  }

  it should "set individual timetout" in {
    val xs = 1 to 125
    val it = xs.iterator
    eventually(timeout(Span(5, Seconds))) {
      Thread.sleep(10)
      it.next should be(110)
    }
  }
}