package core.env

import factory.Factory
import org.apache.spark.SparkEnv
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class MemoryManagerTest extends AnyFlatSpec with Matchers {

  it should "used memory" in {
    Factory.sc.range(1, 10).count()
    val manager = SparkEnv.get.memoryManager
    manager.executionMemoryUsed shouldEqual 0L
    manager.storageMemoryUsed should be > 0L
  }

}
