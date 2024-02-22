package catseffect.docs

import cats.effect.IO
import cats.effect.unsafe.implicits.global
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class HelloWorldTest extends AnyFlatSpec with Matchers {
  it should "get property from config file" in {
    val run = IO.println("Hello, World!")
    run.unsafeRunSync()
  }
}
