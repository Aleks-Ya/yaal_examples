package scala.collection.tuple

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class TupleTest extends AnyFlatSpec with Matchers {

  it should "init a tuple" in {
    val t = (1, "hello", Console)
    val element1 = t._1
    val element2 = t._2
    val element3 = t._3
    element1 shouldEqual 1
    element2 shouldEqual "hello"
    element3 shouldEqual Console
  }

  it should "iterate a tuple" in {
    val t = (1, "hello", "bye")
    val s = t.productIterator.map(value => value.toString).mkString("_")
    s shouldEqual "1_hello_bye"
  }

  it should "convert a tuple to a String" in {
    val t = (1, "hello", "bye")
    val s = t.toString()
    s shouldEqual "(1,hello,bye)"
  }

  it should "swap elements of a tuple" in {
    val t1 = ("hello", "bye")
    val t2 = t1.swap
    t2._1 shouldEqual "bye"
    t2._2 shouldEqual "hello"
  }

}
