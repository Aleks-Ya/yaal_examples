package akka.interaction.ask

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import akka.interaction.ask.NumberStorageActor.{GetAndReplaceNumberMessage, NumberResponse, StoreNumberMessage}

object GuardianActor {

  var receivedNum = 0
  var sent = false

  def apply(): Behavior[NumberResponse] =
    Behaviors.setup { context =>
      val storageActor = context.spawn(NumberStorageActor(), "storage")

      Behaviors.receive { (_, message) =>
        if (!sent) {
          storageActor ! StoreNumberMessage(42)
          storageActor ! GetAndReplaceNumberMessage(77, context.self)
        }
        receivedNum = message.num
        Behaviors.same
      }
    }
}
