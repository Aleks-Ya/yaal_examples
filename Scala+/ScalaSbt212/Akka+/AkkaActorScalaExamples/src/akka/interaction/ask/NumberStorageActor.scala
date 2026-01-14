package akka.interaction.ask

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}

object NumberStorageActor {

  final case class StoreNumberMessage(number: Int)

  final case class GetAndReplaceNumberMessage(number: Int, replyTo: ActorRef[NumberResponse])

  final case class NumberResponse(num: Int)

  var number = 0

  def apply(): Behavior[AnyRef] = Behaviors.receive { (_, message) =>
    message match {
      case m: StoreNumberMessage => number = m.number
      case m: GetAndReplaceNumberMessage =>
        val old = number
        number = m.number
        m.replyTo ! NumberResponse(old)
    }
    Behaviors.same
  }
}
