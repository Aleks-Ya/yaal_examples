package akka.app.currencyconverter

import akka.actor.testkit.typed.scaladsl.ScalaTestWithActorTestKit
import akka.actor.typed.ActorSystem
import org.scalatest.wordspec.AnyWordSpecLike

class CurrencyConverterSpec extends ScalaTestWithActorTestKit with AnyWordSpecLike {

  "A CurrencyConverterActor" must {
    "reply to PrinterActor" in {
      val system = ActorSystem(GuardianActor(), "currencyConverterSystem")
      system ! "start"
    }
  }
}
