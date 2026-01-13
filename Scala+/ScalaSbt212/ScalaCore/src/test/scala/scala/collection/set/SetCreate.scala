package scala.collection.set

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class SetCreate extends AnyFlatSpec with Matchers {

  it should "create a set" in {
    val set = Set(1, 2, 3)
    set should contain allOf(1, 2, 3)
  }

}
