package scala.collection.queue

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.collection.mutable

class QueueMutableTest extends AnyFlatSpec with Matchers {

  it should "create a Queue" in {
    val queue = mutable.Queue[Int](1, 2)
    queue should contain inOrderOnly(1, 2)
  }

  it should "add element to Queue" in {
    val queue = mutable.Queue[Int](1, 2)
    queue += 3
    queue += 4
    queue should contain inOrderOnly(1, 2, 3, 4)
  }

  it should "add collection of elements to Queue" in {
    val queue = mutable.Queue[Int](1, 2)
    queue ++= List(3, 4)
    queue ++= List(5, 6)
    queue should contain inOrderOnly(1, 2, 3, 4, 5, 6)
  }

}
