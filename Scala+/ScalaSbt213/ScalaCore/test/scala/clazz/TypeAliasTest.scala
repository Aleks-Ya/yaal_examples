package scala.clazz

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class TypeAliasTest extends AnyFlatSpec with Matchers {

  it should "use a Typed Alias as a method parameter" in {
    type City = String

    def greetCity(city: City): String = s"Welcome to $city!"

    val london = new City("London")
    val greeting = greetCity(london)
    greeting shouldBe "Welcome to London!"
  }

  it should "create a type alias for a tuple" in {
    type Person = (String, Int, Boolean)
    val john = new Person("John", 30, true)
    john shouldEqual("John", 30, true)
  }

}
