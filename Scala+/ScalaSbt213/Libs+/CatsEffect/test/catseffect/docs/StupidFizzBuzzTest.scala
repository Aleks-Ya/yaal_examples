package catseffect.docs

import cats.effect.IO
import cats.effect.unsafe.implicits.global
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.concurrent.duration._

class StupidFizzBuzzTest extends AnyFlatSpec with Matchers {
  it should "get property from config file" in {
    val run = {
      for {
        ctr <- IO.ref(0)

        wait = IO.sleep(1.second)
        poll = wait *> ctr.get

        _ <- poll.flatMap(IO.println(_)).foreverM.start
        _ <- poll.map(_ % 3 == 0).ifM(IO.println("fizz"), IO.unit).foreverM.start
        _ <- poll.map(_ % 5 == 0).ifM(IO.println("buzz"), IO.unit).foreverM.start

        _ <- (wait *> ctr.update(_ + 1)).foreverM.void
      } yield ()
    }
    val future = run.unsafeRunCancelable()
    Thread.sleep(5000)
    future()
  }
}
