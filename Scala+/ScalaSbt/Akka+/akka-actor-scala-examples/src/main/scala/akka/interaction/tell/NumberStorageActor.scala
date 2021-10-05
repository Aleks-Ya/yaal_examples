package akka.interaction.tell

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors

object NumberStorageActor {

  final case class StoreNumberMessage(number: Int)

  var number = 0

  def apply(): Behavior[StoreNumberMessage] = Behaviors.receive { (_, message) =>
    number = message.number
    Behaviors.same
  }
}
