package scala.clazz.generics

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class GenericClassTest extends AnyFlatSpec with Matchers {

  it should "simple generic class" in {
    class Container[A] {
      private var element: A = _

      def put(e: A): Unit = element = e

      def get(): A = element
    }
    val container = new Container[Int]
    container.put(1)
    container.get() shouldBe 1
  }

  it should "override generic method" in {
    trait Container[A] {
      def put(e: A): Unit

      def get(): A
    }
    class IntContainer extends Container[Int] {
      private var element: Int = _

      override def put(e: Int): Unit = element = e

      override def get(): Int = element
    }
    val container = new IntContainer
    container.put(1)
    container.get() shouldBe 1
  }

  it should "override generic method Option" in {
    trait Container[A] {
      def put(e: Option[A]): Unit

      def get(): Option[A]
    }
    class IntContainer extends Container[Int] {
      private var element: Option[Int] = _

      override def put(e: Option[Int]): Unit = element = e

      override def get(): Option[Int] = element
    }
    val container = new IntContainer
    container.put(Some(1))
    container.get() shouldBe Some(1)
  }

}
