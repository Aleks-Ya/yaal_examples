package scala.collection.seq

import org.scalatest.{FlatSpec, Matchers}

class SearchTest extends FlatSpec with Matchers {

  it should "find first suitable element" in {
    val s = Seq(-1, 0, 3)
    val positive = s.find(n => n > 0)
    positive shouldBe defined
    positive.get shouldEqual 3


    val s2 = Seq(4,5)
    val s3 = s ++ s2
    println(s3)
  }

}
