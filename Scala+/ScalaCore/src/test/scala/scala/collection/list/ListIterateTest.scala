package scala.collection.list

import org.scalatest.{FlatSpec, Matchers}

class ListIterateTest extends FlatSpec with Matchers {

  it should "iterate elements of List" in {
    val list = 1 :: 2 :: Nil
    var sum = 0
    list.foreach((i: Int) => sum += i)
    sum shouldEqual 3
  }

}
