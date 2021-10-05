package akka.state

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import akka.state.CurrencyConverterActor.IncomeMessage

object GuardianActor {

  def apply(): Behavior[String] =
    Behaviors.setup { context =>
      val converter = context.spawn(CurrencyConverterActor(), "converter")
      val printer = context.spawn(CurrencyPrinterActor(), "printer")

      Behaviors.receive { (_, _) =>
        converter ! IncomeMessage(CurrencyCode.USD, CurrencyCode.EUR, BigDecimal(1500.5), printer.narrow)
        Behaviors.same
      }
    }
}