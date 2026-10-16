package scala.number.decimal

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class BigDecimalCreateTest extends AnyFlatSpec with Matchers {

  it should "create a BigDecimal from Int" in {
    val one = BigDecimal(1)
    one.precision shouldBe 1
    one.scale shouldBe 0
    one shouldBe BigDecimal("1")
  }

  it should "create a BigDecimal from Double" in {
    val three = BigDecimal(3.5)
    three.precision shouldBe 2
    three.scale shouldBe 1
    three shouldBe BigDecimal("3.5")
  }

  it should "create a BigDecimal from Int String" in {
    val one = BigDecimal("1")
    one.precision shouldBe 1
    one.scale shouldBe 0
    one shouldBe BigDecimal(1)
  }

  it should "create a BigDecimal from Double String" in {
    val three = BigDecimal("3.5")
    three.precision shouldBe 2
    three.scale shouldBe 1
    three shouldBe BigDecimal(3.5)
  }

  it should "create a BigDecimal from scale" in {
    val three = BigDecimal(35, 1)
    three.precision shouldBe 2
    three.scale shouldBe 1
  }

}
