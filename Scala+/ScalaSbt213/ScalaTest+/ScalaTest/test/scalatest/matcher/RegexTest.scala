package scalatest.matcher

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class RegexTest extends AnyFlatSpec with Matchers {

  it should "include regex" in {
    "hello, world" should include regex "wo.ld"
  }

  it should "start with regex" in {
    "hello, world" should startWith regex "hel*o"
  }

  it should "end with regex" in {
    "hello, world" should endWith regex "wor.d"
  }

  it should "end with regex" in {
    "12345" should fullyMatch regex """\d+"""
  }

}