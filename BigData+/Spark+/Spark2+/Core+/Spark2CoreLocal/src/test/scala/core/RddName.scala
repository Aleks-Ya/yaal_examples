package core

import org.scalatest.{FlatSpec, Matchers}

class RddName extends FlatSpec with Matchers {

  it should "set RDD name" in {
    val rddName = "my_rdd"
    val rdd = Factory.sc.parallelize(Seq(1, 2, 3)).setName(rddName)
    rdd.toString() shouldEqual s"$rddName ParallelCollectionRDD[0] at parallelize at RddName.scala:9"
    rdd.name shouldEqual rddName
  }
}
