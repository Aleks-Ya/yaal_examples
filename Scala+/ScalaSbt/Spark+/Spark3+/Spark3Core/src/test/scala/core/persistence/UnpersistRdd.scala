package core.persistence

import core.Factory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class UnpersistRdd extends AnyFlatSpec with Matchers {

  it should "check is unpersist() recursive" in {
    val sc = Factory.sc

    val rdd1 = sc.parallelize(Seq(1, 2, 3)).cache()
    sc.getPersistentRDDs.values should contain only rdd1

    val rdd2 = rdd1.map(_ * 2).cache()
    sc.getPersistentRDDs.values should contain only(rdd1, rdd2)

    rdd2.unpersist()
    sc.getPersistentRDDs.values should contain only rdd1
  }
}
