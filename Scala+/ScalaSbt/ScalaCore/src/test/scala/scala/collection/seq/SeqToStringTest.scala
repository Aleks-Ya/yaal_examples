package scala.collection.seq

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class SeqToStringTest extends AnyFlatSpec with Matchers {

  it should "convert to a string" in {
    val s = Seq(-1, 0, 3)
    val str = s.toString()
    str shouldEqual "List(-1, 0, 3)"
  }

  it should "convert an empty collection  to a string" in {
    val s = Seq()
    val str = s.toString()
    str shouldEqual "List()"
  }

}
