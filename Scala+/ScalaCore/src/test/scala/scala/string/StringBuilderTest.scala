package scala.string

import org.scalatest.{FlatSpec, Matchers}

class StringBuilderTest extends FlatSpec with Matchers {

  it should "create an empty StringBuilder with constructor" in {
    val sb = new StringBuilder
    sb.toString() shouldBe empty
  }

  it should "create an empty StringBuilder with build method" in {
    val sb = StringBuilder.newBuilder
    sb.toString() shouldBe empty
  }

  it should "create a StringBuilder with initial value" in {
    val sb = new StringBuilder("a")
    sb.toString() shouldEqual "a"
  }

  it should "append StringBuild with a String" in {
    val sb = new StringBuilder
    sb ++= "a"
    sb ++= "b"
    sb.toString() shouldEqual "ab"
  }
}
