package akka.behaviour.change

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}
import akka.behaviour.change.CurrencyCode.{CurrencyCode, EUR, RUR, USD}
import akka.behaviour.change.CurrencyPrinterActor.PrintMessage

object CurrencyConverterActor {

  final case class IncomeMessage(sourceCurrencyCode: CurrencyCode, targetCurrencyCode: CurrencyCode,
                                 sourceAmount: BigDecimal, replyTo: ActorRef[PrintMessage])

  final case class OutcomeMessage(targetCurrencyCode: CurrencyCode, sourceAmount: BigDecimal)

  private val rateMap = Map(RUR -> BigDecimal(1), USD -> BigDecimal(73.5), EUR -> BigDecimal(88.5))

  def apply(): Behavior[IncomeMessage] = Behaviors.receive { (context, message) =>
    val sourceRate = rateMap(message.sourceCurrencyCode)
    val sourceInRur = message.sourceAmount * sourceRate
    val targetRate = rateMap(message.targetCurrencyCode)
    val targetAmount = sourceInRur / targetRate
    val outcomeMessage = PrintMessage(message.targetCurrencyCode, targetAmount)
    message.replyTo ! outcomeMessage
    Behaviors.receive { (_, message) =>
      val outcomeMessage = PrintMessage(message.targetCurrencyCode, 0)
      message.replyTo ! outcomeMessage
      Behaviors.same
    }
  }
}
