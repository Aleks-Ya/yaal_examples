package scala.clazz.`implicit`.parameter

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
  * Source: https://docs.scala-lang.org/tour/implicit-parameters.html
  */
class MonoidExample extends AnyFlatSpec with Matchers {

  it should "use implicit parameters" in {
    abstract class MonoId[A] {
      def add(x: A, y: A): A

      def unit: A
    }

    implicit val stringMonoid: MonoId[String] = new MonoId[String] {
      def add(x: String, y: String): String = x concat y

      def unit: String = ""
    }

    implicit val intMonoid: MonoId[Int] = new MonoId[Int] {
      def add(x: Int, y: Int): Int = x + y

      def unit: Int = 0
    }

    def sum[A](xs: List[A])(implicit m: MonoId[A]): A =
      if (xs.isEmpty) m.unit
      else m.add(xs.head, sum(xs.tail))


    println(sum(List(1, 2, 3))) // uses intMonoid implicitly
    println(sum(List("a", "b", "c"))) // uses stringMonoid implicitly

  }

}
