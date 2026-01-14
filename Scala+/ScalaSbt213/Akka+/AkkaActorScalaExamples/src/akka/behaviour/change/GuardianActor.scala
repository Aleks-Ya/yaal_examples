package akka.behaviour.change

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import akka.behaviour.change.CurrencyConverterActor.IncomeMessage

object GuardianActor {

  def apply(): Behavior[String] =
    Behaviors.setup { context =>
      val converter = context.spawn(CurrencyConverterActor(), "converter")
      val printer = context.spawn(CurrencyPrinterActor(), "printer")

      Behaviors.receive { (_, _) =>
        converter ! IncomeMessage(CurrencyCode.USD, CurrencyCode.EUR, BigDecimal(1500.5), printer.narrow)
        converter ! IncomeMessage(CurrencyCode.USD, CurrencyCode.EUR, BigDecimal(2500.99), printer.narrow)
        Behaviors.same
      }
    }
}
