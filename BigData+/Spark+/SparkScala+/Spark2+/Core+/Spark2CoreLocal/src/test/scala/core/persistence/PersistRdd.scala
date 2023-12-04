package core.persistence

import core.Factory
import org.scalatest.{FlatSpec, Matchers}

class PersistRdd extends FlatSpec with Matchers {

  it should "persist a RDD" in {
    val sc = Factory.sc
    val rdd = sc.parallelize(Seq(1, 2, 3)).cache()
    sc.getPersistentRDDs.values should contain only (rdd)
  }
}
