package scala.clazz

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ConstructorTest extends AnyFlatSpec with Matchers {

  it should "init class without constructor" in {
    class NoConstructorClass {
      val name: String = "abc"
    }
    val person = new NoConstructorClass
    person.name shouldEqual "abc"
  }

  it should "init class fields without constructor" in {
    class NoConstructorFieldClass(val name: String)
    val person = new NoConstructorFieldClass("abc")
    person.name shouldEqual "abc"
  }

  it should "invoke constructor of superclass" in {
    class ParentClass(val name: String)
    class ChildClass(val num: Int) extends ParentClass(num.toString)
    val person = new ChildClass(123)
    person.name shouldEqual "123"
  }

  it should "init class with constructor parameters (var)" in {
    class ConstructorClass {
      var name: String = ""
      var age: Int = 20

      def this(name: String, age: Int) = {
        this()
        this.name = name.toUpperCase()
        this.age = age * 2
      }
    }
    val person = new ConstructorClass("John", 30)
    person.name shouldEqual "JOHN"
    person.age shouldEqual 60
  }

  it should "init class with constructor parameters (val)" in {
    class ConstructorClass(private val name1: String, private val age1: Int) {
      val name: String = name1.toUpperCase()
      val age: Int = age1 * 2
    }
    val person = new ConstructorClass("John", 30)
    person.name shouldEqual "JOHN"
    person.age shouldEqual 60
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
