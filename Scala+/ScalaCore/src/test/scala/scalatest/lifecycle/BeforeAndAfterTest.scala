package scalatest.lifecycle

import org.scalatest.BeforeAndAfter
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class BeforeAndAfterTest extends AnyFlatSpec with BeforeAndAfter with Matchers {

  private var initMe: String = _

  before {
    initMe = "abc"
  }

  "initMe" should "be not null" in {
    initMe should not be null
  }

  after {
    println("AFTER")
  }
}