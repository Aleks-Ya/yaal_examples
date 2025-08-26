package scala.collection.seq

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class SeqToStringTest extends AnyFlatSpec with Matchers {

  it should "convert to a string" in {
    val s = Seq(-1, 0, 3, null)
    val str = s.toString()
    str shouldEqual "List(-1, 0, 3, null)"
  }

  it should "convert an empty collection  to a string" in {
    val s = Seq()
    val str = s.toString()
    str shouldEqual "List()"
  }

  it should "convert String Seq to a string" in {
    val s = Seq("a", "b", "c", null)
    val str = s.toString()
    str shouldEqual "List(a, b, c, null)"
  }

  it should "mkString without arguments" in {
    val s = Seq("a", "b", "c", null)
    val str = s.mkString
    str shouldEqual "abcnull"
  }

  it should "mkString with separator" in {
    val s = Seq("a", "b", "c", null)
    val str = s.mkString(",")
    str shouldEqual "a,b,c,null"
  }

  it should "mkString with separator, start, and end" in {
    val s = Seq("a", "b", "c", null)
    val str = s.mkString("[", ",", "]")
    str shouldEqual "[a,b,c,null]"
  }

}
