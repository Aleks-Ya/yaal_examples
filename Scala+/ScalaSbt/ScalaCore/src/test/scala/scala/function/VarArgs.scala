package scala.function

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class VarArgs extends AnyFlatSpec with Matchers {

  "Function" should "apply varargs argument" in {
    def len(varArgs: Int*): Int = {
      varArgs.length
    }

    len(1, 2) shouldEqual 2
  }
}
