package scala.clazz

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class SingletonTest extends AnyFlatSpec with Matchers {

  it should "read value of a singleton" in {
    object MySingleton {
      val name = "abc"
    }
    val actName = MySingleton.name
    actName shouldEqual "abc"
  }

  it should "singleton with constructor parameters (companion object)" in {
    class MySingleton(name: String, age: Int) {
      override def toString: String = s"$name-$age"
    }
    object MySingleton {
      def apply(name: String, age: Int): MySingleton = new MySingleton(name, age)
    }
    val instance = MySingleton("John", 30)
    instance.toString shouldEqual "John-30"
  }

}
