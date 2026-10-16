package scala.number.decimal

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class BigDecimalModifyTest extends AnyFlatSpec with Matchers {

  it should "sum BigDecimals" in {
    val one = BigDecimal(1)
    val three = BigDecimal(3.5)
    val sum = one + three
    sum shouldBe BigDecimal(4.5)
  }

  it should "reduce precision of a BigDecimal" in {
    val bd1 = BigDecimal(1234, 2)
    val bd2 = bd1.setScale(1, BigDecimal.RoundingMode.HALF_UP)
    bd2 shouldBe BigDecimal("12.3")
  }

  it should "increase precision of a BigDecimal" in {
    val bd1 = BigDecimal(1234, 2)
    bd1.precision shouldBe 4
    val bd2 = bd1.setScale(5, BigDecimal.RoundingMode.HALF_UP)
    bd2 shouldBe BigDecimal("12.34000")
    bd2.precision shouldBe 7
  }

}
