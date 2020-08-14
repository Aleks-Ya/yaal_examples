package akka

import akka.actor.testkit.typed.scaladsl.ScalaTestWithActorTestKit
import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}
import akka.actor.typed.{ActorSystem, Behavior}
import org.scalatest.wordspec.AnyWordSpecLike

class PrintActorHierarchySpec extends ScalaTestWithActorTestKit with AnyWordSpecLike {

  "A Greeter" must {
    "reply to greeted" in {
      val testSystem = ActorSystem(Main(), "testSystem")
      testSystem ! "start"
    }
  }

  object PrintMyActorRefActor {
    def apply(): Behavior[String] =
      Behaviors.setup(context => new PrintMyActorRefActor(context))
  }

  class PrintMyActorRefActor(context: ActorContext[String]) extends AbstractBehavior[String](context) {

    override def onMessage(msg: String): Behavior[String] =
      msg match {
        case "printit" =>
          val secondRef = context.spawn(Behaviors.empty[String], "second-actor")
          println(s"Second: $secondRef")
          this
      }
  }

  object Main {
    def apply(): Behavior[String] =
      Behaviors.setup(context => new Main(context))

  }

  class Main(context: ActorContext[String]) extends AbstractBehavior[String](context) {
    override def onMessage(msg: String): Behavior[String] =
      msg match {
        case "start" =>
          val firstRef = context.spawn(PrintMyActorRefActor(), "first-actor")
          println(s"First: $firstRef")
          firstRef ! "printit"
          this
      }
  }

}
