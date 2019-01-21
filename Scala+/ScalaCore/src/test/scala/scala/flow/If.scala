package scala.flow

import org.scalatest.{FlatSpec, Matchers}

class If extends FlatSpec with Matchers {

  it should "if oneliner" in {
    val a = -3
    val absValue = if (a < 0) -a else a
    absValue shouldBe 3
  }

}
