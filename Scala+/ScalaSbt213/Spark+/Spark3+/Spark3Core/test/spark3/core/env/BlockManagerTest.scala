package spark3.core.env

import org.apache.spark.SparkEnv
import org.apache.spark.storage.{BlockId, BlockManagerId, BlockStatus}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark3.core.Factory

class BlockManagerTest extends AnyFlatSpec with Matchers {

  it should "list all blocks" in {
    Factory.sc.range(1, 10).count
    val blockIds: Seq[BlockId] = SparkEnv.get.blockManager.master.getMatchingBlockIds(
      filter = _ => true,
      askStorageEndpoints = true
    )
    blockIds should not be empty
    blockIds.foreach(println)
  }

  it should "get a block status" in {
    Factory.sc.range(1, 10).count
    val blockIds: Seq[BlockId] = SparkEnv.get.blockManager.master.getMatchingBlockIds(
      filter = _ => true,
      askStorageEndpoints = true
    )
    val blockId = blockIds.head
    val blockStatus: Map[BlockManagerId, BlockStatus] = SparkEnv.get.blockManager.master.getBlockStatus(blockId)
    blockStatus should not be empty
    println(blockStatus)
  }

}
