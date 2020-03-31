package core.checkpoint

import core.Factory
import org.scalatest.{FlatSpec, Matchers}

class LocalCheckpointTest extends FlatSpec with Matchers {

  it should "create a local checkpoint" in {
    val counter = Factory.sc.longAccumulator
    val rdd = Factory.sc.parallelize(Seq(1, 2, 3))
      .map(num => {
        counter.add(1)
        num * 2
      })
    print(rdd.toDebugString)
    rdd.localCheckpoint()
    rdd.isCheckpointed shouldBe false
    print(rdd.toDebugString)

    val count = rdd.count()
    print(rdd.toDebugString)
    rdd.isCheckpointed shouldBe true
    rdd.getCheckpointFile shouldBe empty
    val sum = rdd.sum()

    counter.value shouldBe 3 // 3 times for checkpoint(); count() and sum() uses data from checkpoint
    count shouldBe 3
    sum shouldBe 12
  }
}
