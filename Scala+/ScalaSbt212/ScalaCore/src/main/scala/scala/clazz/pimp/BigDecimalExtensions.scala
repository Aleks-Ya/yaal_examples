package scala.clazz.pimp

object BigDecimalExtensions {
  implicit class BigDecimalExtended(val instance: BigDecimal) extends AnyVal {
    def luckyNumber(): BigDecimal = BigDecimal(777)
  }
}