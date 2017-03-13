package lifecycle

import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

class BeforeAndAfterTest extends FlatSpec with BeforeAndAfter with Matchers {

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