package akka.app.currencyconverter

object CurrencyCode extends Enumeration {
  type CurrencyCode = String
  val RUR = "RUR"
  val USD = "USD"
  val EUR = "EUR"
}
