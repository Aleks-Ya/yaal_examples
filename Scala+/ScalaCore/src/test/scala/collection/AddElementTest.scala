package collection

import org.scalatest.{FlatSpec, Matchers}

import scala.collection.mutable.ListBuffer

class AddElementTest extends FlatSpec with Matchers {

  it should "add element to List" in {
    var list = new ListBuffer[Int]()
    list += 1
    list += 2
    list.toList should contain inOrderOnly(1, 2)
  }

}
