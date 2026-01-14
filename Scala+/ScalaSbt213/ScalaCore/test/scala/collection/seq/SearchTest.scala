package scala.collection.seq

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class SearchTest extends AnyFlatSpec with Matchers {

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
