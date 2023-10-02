package scala.clazz.singleton

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class SingletonInheritanceTest extends AnyFlatSpec with Matchers {

  "singleton" should "inherit a class" in {
    class Parent {
      protected val text = "abc"
    }
    object MySingleton extends Parent {
      val data: String = "Info " + text
    }
    MySingleton.data shouldEqual "Info abc"
  }

  "singleton" should "inherit a class with constructor (static param)" in {
    class Parent(val text: String) {
    }
    val parentText = "abc"
    object MySingleton extends Parent(parentText) {
      val data: String = "Info " + text
    }
    MySingleton.data shouldEqual "Info abc"
  }

  "singleton" should "inherit a class with constructor (dynamic param)" in {
    class Parent(val parentText: String) {
    }
    class MySingleton private(val classText: String) extends Parent(classText) {
    }
    object MySingleton {
      def apply(objectText: String): MySingleton = new MySingleton(objectText)
    }
    val singleton = MySingleton("abc")
    singleton.classText shouldEqual "abc"
    singleton.parentText shouldEqual "abc"
  }

}
