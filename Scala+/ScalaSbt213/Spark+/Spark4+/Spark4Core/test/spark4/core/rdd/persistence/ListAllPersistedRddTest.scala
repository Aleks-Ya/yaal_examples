package spark4.core.rdd.persistence

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark4.core.Factory

class ListAllPersistedRddTest extends AnyFlatSpec with Matchers {

  it should "enumerate all persisted RDD" in {
    val sc = Factory.sc
    sc.getPersistentRDDs shouldBe empty

    val rdd = sc.parallelize(Seq(1, 2, 3))
    sc.getPersistentRDDs shouldBe empty

    rdd.cache()
    sc.getPersistentRDDs.values should contain only rdd

    rdd.unpersist()
    sc.getPersistentRDDs shouldBe empty
  }

}
