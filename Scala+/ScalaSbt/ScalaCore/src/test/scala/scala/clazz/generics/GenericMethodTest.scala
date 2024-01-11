package scala.clazz.generics

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class GenericMethodTest extends AnyFlatSpec with Matchers {

  it should "simple generic method" in {
    class Container {
      private var element: Any = _

      def put[A](e: A): Unit = element = e

      def get[A](): A = element.asInstanceOf[A]
    }
    val container = new Container
    container.put(1)
    val value1: Int = container.get()
    value1 shouldBe 1
  }

  it should "override generic method" in {
    trait Container {
      def put[A](e: A): Unit

      def get[A](): A
    }
    class IntContainer extends Container {
      private var element: Int = _

      override def put[Int](e: Int): Unit = element = e.asInstanceOf[scala.Int]

      override def get[Int](): Int = element.asInstanceOf[Int]
    }
    val container = new IntContainer
    container.put(1)
    val value1: Int = container.get()
    value1 shouldBe 1
  }
}
