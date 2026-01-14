package core.env

import factory.Factory
import org.apache.spark.SparkEnv
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class SparkEnvTest extends AnyFlatSpec with Matchers {

  it should "get memory manager" in {
    Factory.sc.range(1, 10)
    val manager = SparkEnv.get.memoryManager
    manager.executionMemoryUsed shouldEqual 0L
  }

}
