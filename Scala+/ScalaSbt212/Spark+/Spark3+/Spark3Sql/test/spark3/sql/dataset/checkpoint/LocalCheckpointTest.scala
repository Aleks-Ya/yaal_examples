package spark3.sql.dataset.checkpoint

import org.apache.spark.sql.Dataset
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark3.sql.{City, Factory}

class LocalCheckpointTest extends AnyFlatSpec with Matchers {

  it should "create a local checkpoint" in {
    val ds: Dataset[City] = Factory.cityDs
    ds.rdd.isCheckpointed shouldBe false
    ds.rdd.getCheckpointFile shouldBe empty
    println(s"Before checkpoint:\n${ds.rdd.toDebugString}")

    val checkpointedDs: Dataset[City] = ds.localCheckpoint()
    checkpointedDs.rdd.isCheckpointed shouldBe false
    checkpointedDs.rdd.getCheckpointFile shouldBe empty
    println(s"After checkpoint:\n${checkpointedDs.rdd.toDebugString}")

    checkpointedDs.count shouldBe 3
    checkpointedDs.rdd.isCheckpointed shouldBe false
    checkpointedDs.rdd.getCheckpointFile shouldBe empty
    println(s"After action:\n${checkpointedDs.rdd.toDebugString}")
  }

}
