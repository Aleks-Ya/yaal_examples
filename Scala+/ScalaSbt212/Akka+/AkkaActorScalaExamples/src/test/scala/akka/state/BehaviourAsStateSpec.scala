package akka.state

import akka.actor.testkit.typed.scaladsl.ScalaTestWithActorTestKit
import akka.actor.typed.ActorSystem
import org.scalatest.wordspec.AnyWordSpecLike

/**
 * Hold Actor state as behaviour instead of a variable.
 */
class BehaviourAsStateSpec extends ScalaTestWithActorTestKit with AnyWordSpecLike {

  "A CurrencyConverterActor" must {
    "reply to PrinterActor" in {
      val system = ActorSystem(GuardianActor(), "currencyConverterSystem")
      system ! "start"
    }
  }
}
