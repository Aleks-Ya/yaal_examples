package scala.flow

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ForYieldTest extends AnyFlatSpec with Matchers {
  it should "convert a list" in {
    val list = 1 :: 2 :: 3 :: Nil
    val actual = for (n <- list) yield n * 2
    actual shouldEqual Seq(2, 4, 6)
  }

  it should "use a code block after yield" in {
    val list = 1 :: 2 :: 3 :: Nil
    val actual = for (n <- list) yield {
      val multiplier = 2
      n * multiplier
    }
    actual shouldEqual Seq(2, 4, 6)
  }

  it should "use multiple generators" in {
    val numbers = 1 :: 2 :: 3 :: Nil
    val characters = "a" :: "b" :: Nil
    val actual = for {
      n <- numbers
      c <- characters
    } yield s"$c-$n"
    actual shouldEqual Seq("a-1", "b-1", "a-2", "b-2", "a-3", "b-3")
  }

  it should "use multiple generators (ignoring one variable)" in {
    val numbers = 1 :: 2 :: 3 :: Nil
    val characters = "a" :: "b" :: Nil
    val punctuations = "." :: "," :: Nil
    val actual = for {
      n <- numbers
      c <- characters
      _ <- punctuations
    } yield s"$c-$n"
    actual shouldEqual Seq("a-1", "a-1", "b-1", "b-1", "a-2", "a-2", "b-2", "b-2", "a-3", "a-3", "b-3", "b-3")
  }
}
