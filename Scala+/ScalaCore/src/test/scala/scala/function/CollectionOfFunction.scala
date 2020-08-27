package scala.function

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class CollectionOfFunction extends AnyFlatSpec with Matchers {

  it should "create a Seq of functions" in {
    val functions: Seq[(Int, Int) => Int] = Seq(
      (a: Int, b: Int) => a + b,
      (a: Int, b: Int) => a - b
    )
    functions.head(3, 7) shouldEqual 10
    functions(1)(6, 1) shouldEqual 5

    val functions2: Seq[String => String] = Seq(
      (s: String) => s.toUpperCase,
      (n: String) => n.toLowerCase
    )
    functions2.head("abc") shouldEqual "ABC"

  }
}
