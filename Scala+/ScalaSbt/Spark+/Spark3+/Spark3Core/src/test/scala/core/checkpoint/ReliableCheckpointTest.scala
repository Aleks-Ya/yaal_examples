package core.checkpoint

import java.nio.file.Files

import core.Factory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ReliableCheckpointTest extends AnyFlatSpec with Matchers {

  it should "create a reliable checkpoint without persisting" in {
    val checkpointDir = Files.createTempDirectory("ReliableCheckpointTest_")
    print(s"Checkpoint directory: $checkpointDir")
    Factory.sc.setCheckpointDir(checkpointDir.toString)

    val counter = Factory.sc.longAccumulator
    val rdd = Factory.sc.parallelize(Seq(1, 2, 3))
      .map(num => {
        counter.add(1)
        num * 2
      })
    print(rdd.toDebugString)
    //    rdd.persist()
    //    print(rdd.toDebugString)
    rdd.checkpoint()
    rdd.isCheckpointed shouldBe false
    print(rdd.toDebugString)

    val count = rdd.count()
    print(rdd.toDebugString)
    rdd.isCheckpointed shouldBe true
    rdd.getCheckpointFile should not be empty
    val sum = rdd.sum()

    counter.value shouldBe 6 // 3 times for checkpoint() + 3 times for count(); sum() uses data from checkpoint
    count shouldBe 3
    sum shouldBe 12
  }
}
