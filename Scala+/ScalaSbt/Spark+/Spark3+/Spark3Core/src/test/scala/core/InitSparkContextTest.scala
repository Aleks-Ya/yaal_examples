package core

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class InitSparkContextTest extends AnyFlatSpec with Matchers {

  "SparkContext" should "be initialized" in {
    val rdd = Factory.sc.range(1, 10)
    val sum = rdd.reduce(_ + _)
    sum shouldEqual 45
  }
}
