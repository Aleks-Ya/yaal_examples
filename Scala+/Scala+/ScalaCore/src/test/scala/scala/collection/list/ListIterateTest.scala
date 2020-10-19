package scala.collection.list

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ListIterateTest extends AnyFlatSpec with Matchers {

  it should "iterate elements of List" in {
    val list = 1 :: 2 :: Nil
    var sum = 0
    list.foreach((i: Int) => sum += i)
    sum shouldEqual 3
  }

}
