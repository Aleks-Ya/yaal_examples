package scala.collection.list

import org.scalatest.{FlatSpec, Matchers}

import scala.collection.mutable.ListBuffer

class ListAddTest extends FlatSpec with Matchers {

  it should "add element to List" in {
    val list = new ListBuffer[Int]()
    list += 1
    list += 2
    list.toList should contain inOrderOnly(1, 2)
  }

  it should "add collection of elements to List" in {
    val list = new ListBuffer[Int]()
    list ++= List(1, 2)
    list ++= List(3, 4)
    list.toList should contain inOrderOnly(1, 2, 3, 4)
  }

}
