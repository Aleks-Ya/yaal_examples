package scala.number

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class BigDecimalTest extends AnyFlatSpec with Matchers {

  it should "create a BigDecimal from Number" in {
    val one = BigDecimal(1)
    val three = BigDecimal(3.5)
    val sum = one + three
    sum shouldBe BigDecimal(4.5)
  }

  it should "create a BigDecimal from String" in {
    val one = BigDecimal("1")
    val three = BigDecimal("3.5")
    val sum = one + three
    sum shouldBe BigDecimal(4.5)
  }

}
