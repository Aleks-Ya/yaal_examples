package scalamock

import org.scalamock.scalatest.MockFactory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class AmbiguousReference extends AnyFlatSpec with Matchers with MockFactory {

  it should """solve "scala mock ambiguous reference to overloaded definition""" in {
    trait Formatter {
      def format(s: String): String

      def format(n: Int): String
    }
    val formatterMock = mock[Formatter]
    val expStr = "Russia"
    val expInt = "30"
    (formatterMock.format(_: String)).expects(*).returning(expStr)
    (formatterMock.format(_: Int)).expects(*).returning(expInt)
    formatterMock.format("abc") shouldEqual expStr
    formatterMock.format(123) shouldEqual expInt
  }

}