package scala.function

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class MethodParameterDefaultValueTest extends AnyFlatSpec with Matchers {

  private def replaceSpaces(str: String, replacement: String = "_"): String =
    str.replace(" ", replacement)

  it should "use default parameter value" in {
    replaceSpaces("Hello World") shouldEqual "Hello_World"
    replaceSpaces("Hello World", "-") shouldEqual "Hello-World"
  }

  private def replaceSpacesOpt(str: String, replacementOpt: Option[String] = None): String =
    replacementOpt.map(replacement => str.replace(" ", replacement)).getOrElse(str)

  it should "use optional default parameter value" in {
    replaceSpacesOpt("Hello World") shouldEqual "Hello World"
    replaceSpacesOpt("Hello World", Some("-")) shouldEqual "Hello-World"
  }

  private def replaceSpacesNull(str: String, replacement: String = null): String =
    Option(replacement).map(str.replace(" ", _)).getOrElse(str)

  it should "use null default parameter value with String" in {
    replaceSpacesNull("Hello World") shouldEqual "Hello World"
    replaceSpacesNull("Hello World", "-") shouldEqual "Hello-World"
  }

  /**
   * `null` default value leads to runtime error `an expression of type Null is ineligible for implicit conversion`.
   */
  private def replaceSpacesInt(str: String, replacement: Int = -1): String =
    if (replacement == -1)
      str
    else
      str.replace(" ", replacement.toString)

  it should "use null default parameter value with Int" in {
    replaceSpacesInt("Hello World") shouldEqual "Hello World"
    replaceSpacesInt("Hello World", 777) shouldEqual "Hello777World"
  }

}
