package spark4.core.env

import org.apache.spark.SparkEnv
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark4.core.Factory

class SparkEnvTest extends AnyFlatSpec with Matchers {

  it should "get memory manager" in {
    Factory.sc.range(1, 10)
    val manager = SparkEnv.get.memoryManager
    manager.executionMemoryUsed shouldEqual 0L
  }

  it should "get block manager" in {
    Factory.sc.range(1, 10)
    val executorBlockManager = SparkEnv.get.blockManager
    val masterBlockManager = executorBlockManager.master
    executorBlockManager should not be null
    masterBlockManager should not be null
  }

  it should "get shuffle manager" in {
    Factory.sc.range(1, 10)
    val manager = SparkEnv.get.shuffleManager
    manager should not be null
  }

}
