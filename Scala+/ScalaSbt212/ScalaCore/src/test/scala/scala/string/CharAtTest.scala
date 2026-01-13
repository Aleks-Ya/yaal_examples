package scala.string

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class CharAtTest extends AnyFlatSpec with Matchers {

  it should "get a symbol from a string at specific position" in {
    val s = "abcdefg"
    val c0 = s.charAt(0)
    val c3 = s.charAt(3)
    c0 shouldEqual 'a'
    c3 shouldEqual 'd'
  }

  it should "convert a String to a List of Chars" in {
    val s = "abc"
    val l = s.toList
    l should contain inOrderOnly('a', 'b', 'c')
  }

}
