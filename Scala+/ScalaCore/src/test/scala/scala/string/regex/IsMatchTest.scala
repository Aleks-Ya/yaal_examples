package scala.string.regex

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class IsMatchTest extends AnyFlatSpec with Matchers {

  it should "does match" in {
    val text = "Hello John now!"
    val matches =  text.matches("""Hello\s(\w+)\snow!""")
    matches shouldBe true
  }

}
