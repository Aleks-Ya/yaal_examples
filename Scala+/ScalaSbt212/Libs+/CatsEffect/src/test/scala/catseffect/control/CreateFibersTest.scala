package catseffect.control

import cats.effect.IO
import cats.effect.testing.scalatest.AsyncIOSpec
import org.scalatest.freespec.AsyncFreeSpec
import org.scalatest.matchers.should.Matchers

class CreateFibersTest extends AsyncFreeSpec with AsyncIOSpec with Matchers {
  "Create several fibers" - {

    "without return value" in {
      val fiber1 = IO.println("Hello from Fiber 1").start
      val fiber2 = IO.println("Hello from Fiber 2").start
      for {
        f1 <- fiber1
        f2 <- fiber2
        _ <- f1.join
        _ <- f2.join
      } yield ()
    }

    "with return value" in {
      val fiber1 = IO.pure(7).start
      val fiber2 = IO.pure(3).start
      val res = for {
        f1 <- fiber1
        f2 <- fiber2
        r1 <- f1.joinWithNever
        r2 <- f2.joinWithNever
      } yield r1 * r2
      res.asserting(_ shouldBe 21)
    }
  }
}
