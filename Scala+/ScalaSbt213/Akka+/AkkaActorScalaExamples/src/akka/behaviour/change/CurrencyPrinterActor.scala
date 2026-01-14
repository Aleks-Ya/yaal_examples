package akka.behaviour.change

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import akka.behaviour.change.CurrencyCode.CurrencyCode

object CurrencyPrinterActor {

  final case class PrintMessage(currencyCode: CurrencyCode, amount: BigDecimal)

  def apply(): Behavior[PrintMessage] = Behaviors.receive { (_, message) =>
    println(message.amount + " " + message.currencyCode)
    Behaviors.same
  }
}
