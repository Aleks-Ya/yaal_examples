package spark3.core.rdd.persistence

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark3.core.Factory

class PersistRddTest extends AnyFlatSpec with Matchers {

  it should "persist a RDD" in {
    val sc = Factory.sc
    val rdd = sc.parallelize(Seq(1, 2, 3)).cache()
    sc.getPersistentRDDs.values should contain only rdd
  }

}
