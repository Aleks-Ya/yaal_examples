package akka.interaction.tell

import akka.actor.testkit.typed.scaladsl.ScalaTestWithActorTestKit
import akka.actor.typed.ActorSystem
import org.scalatest.wordspec.AnyWordSpecLike

/**
 * Tell (one-direction) interaction between Actors.
 */
class TellInteractionSpec extends ScalaTestWithActorTestKit with AnyWordSpecLike {

  "GuardianActor" must {
    "tell to NumberStorageActor" in {
      val system = ActorSystem(GuardianActor(), "currencyConverterSystem")
      NumberStorageActor.number shouldBe 0
      system ! "start"
      system.terminate()
      NumberStorageActor.number shouldBe 42
    }
  }
}
