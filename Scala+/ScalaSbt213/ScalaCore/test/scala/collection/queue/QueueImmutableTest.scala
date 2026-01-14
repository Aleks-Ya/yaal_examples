package scala.collection.queue

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.collection.immutable.Queue

class QueueImmutableTest extends AnyFlatSpec with Matchers {
  it should "create a Queue" in {
    val queue = Queue[Int](1, 2, 3)
    queue should contain inOrderOnly(1, 2, 3)
  }
}
