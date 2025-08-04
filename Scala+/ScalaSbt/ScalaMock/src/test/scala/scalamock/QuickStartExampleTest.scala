package scalamock

import org.scalamock.scalatest.MockFactory
import org.scalatest.Inspectors
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scalamock.Greetings.Formatter

/**
 * Examples from "Quick Start" (http://scalamock.org/quick-start).
 */
class QuickStartExampleTest extends AnyFlatSpec with Matchers with Inspectors with MockFactory {

  it should "say hello" in {
    val mockFormatter = mock[Formatter]
    (mockFormatter.format _).expects("Mr Bond").returning("Ah, Mr Bond. I've been expecting you").once()
    Greetings.sayHello("Mr Bond", mockFormatter)
  }

  it should "use stab" in {
    val mockFormatter = stub[Formatter]
    val bond = "Mr Bond"
    (mockFormatter.format _).when(bond).returns("Ah, Mr Bond. I've been expecting you")
    Greetings.sayHello(bond, mockFormatter)
    (mockFormatter.format _).verify(bond).once()
  }

  it should "throw exception" in {
    val brokenFormatter = mock[Formatter]
    (brokenFormatter.format _).expects(*).throwing(new NullPointerException).anyNumberOfTimes()
    intercept[NullPointerException] {
      Greetings.sayHello("Erza", brokenFormatter)
    }
  }

  it should "respond dynamically for parameter" in {
    val australianFormat = mock[Formatter]
    (australianFormat.format _).expects(*).onCall { s: String => s"G'day $s" }.twice()
    Greetings.sayHello("Wendy", australianFormat)
    Greetings.sayHello("Gray", australianFormat)
  }

  it should "verifying parameters dynamically (two flavours)" in {
    val teamNatsu = Set("Natsu", "Lucy", "Happy", "Erza", "Gray", "Wendy", "Carla")
    val formatter = mock[Formatter]

    def assertTeamNatsu(s: String): Unit = {
      assert(teamNatsu.contains(s))
    }

    // argAssert fails early
    (formatter.format _).expects(argAssert(assertTeamNatsu _)).onCall { s: String => s"Yo $s" }.once()

    // 'where' verifies at the end of the test
    (formatter.format _).expects(where { s: String => teamNatsu contains s }).onCall { s: String => s"Yo $s" }.twice()

    Greetings.sayHello("Carla", formatter)
    Greetings.sayHello("Happy", formatter)
    Greetings.sayHello("Lucy", formatter)
  }

  it should "verify call order" in {
    val mockFormatter = mock[Formatter]

    inAnyOrder {
      (mockFormatter.format _).expects("Mr Bond").returns("Ah, Mr Bond. I've been expecting you")
      (mockFormatter.format _).expects("Natsu").returns("Not now Natsu!").atLeastTwice()
    }

    Greetings.sayHello("Natsu", mockFormatter)
    Greetings.sayHello("Natsu", mockFormatter)
    Greetings.sayHello("Mr Bond", mockFormatter)
    Greetings.sayHello("Natsu", mockFormatter)
  }

}

object Greetings {

  trait Formatter {
    def format(s: String): String
  }

  object EnglishFormatter {
    def format(s: String): String = s"Hello $s"
  }

  object GermanFormatter {
    def format(s: String): String = s"Hallo $s"
  }

  object JapaneseFormatter {
    def format(s: String): String = s"こんにちは $s"
  }

  def sayHello(name: String, formatter: Formatter): Unit = {
    println(formatter.format(name))
  }
}
