package core.transformation

import core.Factory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ReduceByKeyTest extends AnyFlatSpec with Matchers {

  "RDD" should "process Scala's tuple" in {
    val list = Factory.sc.parallelize(Seq("a", "b"))
      .map(word => (word, 1))
      .reduceByKey(_ + _)
      .map((tuple: (String, Int)) => tuple._1 + "-" + tuple._2)
      .collect
    println(list.mkString("Array(", ", ", ")"))
    list should contain inOrderOnly("a-1", "b-1")
  }
}
