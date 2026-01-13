package akka.interaction.tell

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import akka.interaction.tell.NumberStorageActor.StoreNumberMessage

object GuardianActor {

  def apply(): Behavior[String] =
    Behaviors.setup { context =>
      val storageActor = context.spawn(NumberStorageActor(), "storage")

      Behaviors.receive { (_, _) =>
        storageActor ! StoreNumberMessage(42)
        Behaviors.same
      }
    }
}
