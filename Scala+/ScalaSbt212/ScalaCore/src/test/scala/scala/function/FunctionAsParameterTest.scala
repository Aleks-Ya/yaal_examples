package scala.function

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class FunctionAsParameterTest extends AnyFlatSpec with Matchers {

  it should "use a function as a method parameter" in {
    val function = (a: Int) => s"$a years old"
    val age = 30
    val res = Formatter.format(age, function)
    res shouldEqual "I'm 30 years old"
  }

  private object Formatter {
    def format(age: Int, converter: Int => String): String = {
      val text = converter(age)
      s"I'm $text"
    }
  }
}
