package scala.function

import org.scalatest.{FlatSpec, Matchers}

class VarArgs extends FlatSpec with Matchers {

  "Function" should "apply varargs argument" in {
    def len(varArgs: Int*): Int = {
      varArgs.length
    }

    len(1, 2) shouldEqual 2
  }
}
