package scala.function

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class VarArgsTest extends AnyFlatSpec with Matchers {

  "Function" should "apply varargs argument" in {
    def len(varArgs: Int*): Int = varArgs.length

    len(1, 2) shouldEqual 2
    val args = List(1, 4, 6)
    len(args: _*) shouldBe 3
  }

  "Function" should "apply tuple vararg" in {
    def concatenate(people: (String, Int)*): String =
      people.map(person => s"${person._1}-${person._2}").mkString(", ")

    concatenate(("John", 30), ("Mary", 25)) shouldEqual "John-30, Mary-25"

    val people = Seq(("John", 30), ("Mary", 25))
    concatenate(people: _*) shouldEqual "John-30, Mary-25"
  }

  "Function" should "apply a nested tuple vararg (not supported)" in {
    def concatenate(people: (String, Seq[Int])*): String =
      people.map(person => s"${person._1}: ${person._2.mkString("-")}").mkString(", ")

    concatenate(("John", Seq(10, 20, 30)), ("Mary", Seq(5, 6, 7))) shouldEqual "John: 10-20-30, Mary: 5-6-7"
  }
}
