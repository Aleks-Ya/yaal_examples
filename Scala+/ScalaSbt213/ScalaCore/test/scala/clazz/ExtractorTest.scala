package scala.clazz

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * @see https://docs.scala-lang.org/tour/extractor-objects.html
 */
class ExtractorTest extends AnyFlatSpec with Matchers {

  it should "extract a constructor argument" in {
    object CustomerID {

      def apply(name: String) = s"$name--customer"

      def unapply(customerID: String): Option[String] = {
        val stringArray: Array[String] = customerID.split("--")
        if (stringArray.tail.nonEmpty) Some(stringArray.head) else None
      }
    }

    val expName = "John"
    val customerId = CustomerID(expName)
    customerId shouldBe "John--customer"
    val CustomerID(actName) = customerId
    actName shouldBe "John"
  }

}
