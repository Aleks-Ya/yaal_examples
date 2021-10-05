package akka.state

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import akka.state.CurrencyCode.CurrencyCode

object CurrencyPrinterActor {

  final case class PrintMessage(currencyCode: CurrencyCode, amount: BigDecimal)

  def apply(): Behavior[PrintMessage] = Behaviors.receive { (_, message) =>
    print(message.amount + " " + message.currencyCode)
    Behaviors.same
  }
}
