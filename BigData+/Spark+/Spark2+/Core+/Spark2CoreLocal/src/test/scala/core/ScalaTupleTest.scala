package core

import org.scalatest.{FlatSpec, Matchers}

class ScalaTupleTest extends FlatSpec with Matchers {

  "RDD" should "process Scala's tuple" in {
    val list = Factory.sc.parallelize(Seq("a", "b"))
      .map(word => (word, 1))
      .map(pair => pair._1 + "-" + pair._2)
      .collect
    list should contain inOrderOnly("a-1", "b-1")

  }
}
