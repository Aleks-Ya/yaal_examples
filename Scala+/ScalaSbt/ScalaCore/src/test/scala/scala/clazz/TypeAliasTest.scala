package scala.clazz

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class TypeAliasTest extends AnyFlatSpec with Matchers {

  type City = String

  def greetCity(city: City): String = s"Welcome to $city!"

  it should "use a Typed Alias as a method parameter" in {
    val london = new City("London")
    val greeting = greetCity(london)
    greeting shouldBe "Welcome to London!"
  }

}
