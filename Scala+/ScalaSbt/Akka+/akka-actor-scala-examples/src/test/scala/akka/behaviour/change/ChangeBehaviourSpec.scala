package akka.behaviour.change

import akka.actor.testkit.typed.scaladsl.ScalaTestWithActorTestKit
import akka.actor.typed.ActorSystem
import org.scalatest.wordspec.AnyWordSpecLike

/**
 * Actor changes its behaviour after receiving a message.
 */
class ChangeBehaviourSpec extends ScalaTestWithActorTestKit with AnyWordSpecLike {

  "A CurrencyConverterActor" must {
    "reply to PrinterActor" in {
      val system = ActorSystem(GuardianActor(), "currencyConverterSystem")
      system ! "start"
    }
  }
}
