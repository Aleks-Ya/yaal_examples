package scala.clazz

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ConstructorTest extends AnyFlatSpec with Matchers {


  it should "init class without constructor" in {
    class NoConstructorClass {
      var name: String = "abc"
    }
    val person = new NoConstructorClass
    person.name shouldEqual "abc"
  }

  it should "init class with constructor parameters" in {
    class ConstructorClass {
      var name: String = ""
      var age: Int = 20

      def this(name: String, age: Int) = {
        this()
        this.name = name
        this.age = age
      }
    }
    val name = "John"
    val age = 30
    val person = new ConstructorClass(name, age)
    person.name shouldEqual name
    person.age shouldEqual age
  }

  it should "init class with default values in constructor" in {
    class ConstructorWithDefaultValuesClass(var name: String = "John", var age: Int = 30)
    val name = "John"
    val age = 30
    val person = new ConstructorWithDefaultValuesClass
    person.name shouldEqual name
    person.age shouldEqual age
  }

}
