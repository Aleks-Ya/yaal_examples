package scala.flow

import org.scalatest.{FlatSpec, Matchers}

import scala.util.control.Breaks._

class BreakLoop extends FlatSpec with Matchers {

  it should "break a for loop" in {
    val list = 1 :: 2 :: 3 :: Nil
    var sum = 0
    breakable {
      for (x <- list) {
        sum += x
        if (sum > 2) break
      }
    }
    sum shouldEqual 3
  }

  it should "break a while loop" in {
    var counter = 0
    breakable {
      while (true) {
        counter += 1
        if (counter == 2) break
      }
    }
    counter shouldEqual 2
  }

}
