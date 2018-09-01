package core.transformation

import core.Factory
import org.scalatest.{FlatSpec, Matchers}

class ReduceByKeyTest extends FlatSpec with Matchers {

  "RDD" should "process Scala's tuple" in {
    val list = Factory.sc.parallelize(Seq("a", "b"))
      .map(word => (word, 1))
      .reduceByKey(_ + _)
      .map((tuple: (String, Int)) => tuple._1 + "-" + tuple._2)
      .collect
    println(list)
    list should contain inOrderOnly("a-1", "b-1")
  }
}
