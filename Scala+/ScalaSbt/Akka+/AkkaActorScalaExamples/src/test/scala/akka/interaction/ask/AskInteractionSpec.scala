package akka.interaction.ask

import akka.actor.testkit.typed.scaladsl.ScalaTestWithActorTestKit
import akka.actor.typed.ActorSystem
import akka.interaction.ask.NumberStorageActor.NumberResponse
import org.scalatest.wordspec.AnyWordSpecLike

/**
 * Ask (two-direction) interaction between Actors.
 */
class AskInteractionSpec extends ScalaTestWithActorTestKit with AnyWordSpecLike {

  "GuardianActor" must {
    "ask NumberStorageActor" in {
      val system = ActorSystem(GuardianActor(), "currencyConverterSystem")
      NumberStorageActor.number shouldBe 0
      GuardianActor.receivedNum shouldBe 0
      system ! NumberResponse(0)
      system terminate()
      NumberStorageActor.number shouldBe 77
      GuardianActor.receivedNum shouldBe 42
    }
  }
}
