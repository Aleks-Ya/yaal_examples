package core

import org.scalatest.{FlatSpec, Matchers}

class InitSparkContextTest extends FlatSpec with Matchers {

  "SparkContext" should "be initialized" in {
    val rdd = Factory.sc.range(1, 10)
    val sum = rdd.reduce(_ + _)
    sum shouldEqual 45
  }
}
