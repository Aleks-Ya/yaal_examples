package scala.string

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class StringInterpolationTest extends AnyFlatSpec with Matchers {

  "s-interpolation" should "format string with parameters" in {
    val name = "John"
    val text = s"User name is $name"
    text shouldEqual "User name is John"
  }

  "f-interpolation" should "format string with parameters" in {
    val price = 1.5
    val count = 3
    val title = "oranges"
    val text = f"Price of $count%d items of $title%s is $price%2.2f"
    text shouldEqual "Price of 3 items of oranges is 1.50"
  }

  "raw-interpolation" should "don't escape the special symbols" in {
    val text = raw"a\nb"
    text shouldEqual "a\\nb"
  }

  "triple quotes" should "don't escape the special symbols" in {
    val text = """a\nb"""
    text shouldEqual "a\\nb"
  }

  "format method" should "substitute variables" in {
    "Name: %s, Age: %d".format("John", 30) shouldEqual "Name: John, Age: 30"
    raw"Name: \%s\, Age: \%d\".format("John", 30) shouldEqual "Name: \\John\\, Age: \\30\\"
    """Name: \%s\, Age: \%d\""".format("John", 30) shouldEqual "Name: \\John\\, Age: \\30\\"
  }

}
