package scala.clazz

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class AnonymousClass extends AnyFlatSpec with Matchers {

  it should "instantiate anonymous class" in {
    class Information {
      def info() = "Parent Info"
    }
    val expInfo = "Anonymous Info"
    val anonymous = new Information {
      override def info(): String = expInfo
    }
    anonymous.info() shouldBe expInfo
  }

}
