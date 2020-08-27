package scala.number

import java.math.BigDecimal
import java.text.NumberFormat

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class FormatNumbers extends AnyFlatSpec with Matchers {

  it should "format Integer with a Locale" in {
    val locale = new java.util.Locale("ru", "RU")
    val formatter = NumberFormat.getIntegerInstance(locale)
    formatter.format(1000) shouldEqual "1 000"
    formatter.format(1000000L) shouldEqual "1 000 000"
    formatter.format(1000000.123) shouldEqual "1 000 000"
  }

  it should "format Double with a Locale" in {
    val locale = new java.util.Locale("ru", "RU")
    val formatter = NumberFormat.getInstance(locale)
    formatter.format(1000) shouldEqual "1 000"
    formatter.format(1000000L) shouldEqual "1 000 000"
    formatter.format(1000000.123) shouldEqual "1 000 000,123"
  }

  it should "format java.math.BigDecimal with a Locale" in {
    val num = BigDecimal.valueOf(10000000.236000000)
    val locale = new java.util.Locale("ru", "RU")
    val formatter = NumberFormat.getInstance(locale)
    formatter.format(num) shouldEqual "10 000 000,236"
  }

}
