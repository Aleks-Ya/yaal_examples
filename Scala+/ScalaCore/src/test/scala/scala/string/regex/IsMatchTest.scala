package scala.string.regex

import org.scalatest.{FlatSpec, Matchers}

class IsMatchTest extends FlatSpec with Matchers {

  it should "does match" in {
    val text = "Hello John now!"
    val matches =  text.matches("""Hello\s(\w+)\snow!""")
    matches shouldBe true
  }

}
