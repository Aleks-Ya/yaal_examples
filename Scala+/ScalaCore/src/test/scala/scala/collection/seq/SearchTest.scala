package scala.collection.seq

import org.scalatest.{FlatSpec, Matchers}

class SearchTest extends FlatSpec with Matchers {

  it should "find first suitable element" in {
    val s = Seq(-1, 0, 3)
    val positive = s.find(n => n > 0)
    positive shouldEqual 3
  }

}
