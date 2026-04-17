package spark4.core.rdd.partitioner

import org.apache.spark.RangePartitioner
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark4.core.Factory
import spark4.core.Utils.partitionedRddToString

class RangePartitionerTest extends AnyFlatSpec with Matchers {
  private val data = Seq((1, "a"), (2, "b"), (3, "c"), (4, "d"), (5, "e"))
  private val partitionNumber = 3

  it should "Use RangePartitioner" in {
    val originRdd = Factory.sc.parallelize(data)
    originRdd.getNumPartitions shouldBe 1
    val partitioner = new RangePartitioner(partitionNumber, originRdd)
    val partitionedRdd = originRdd.partitionBy(partitioner)
    partitionedRdd.getNumPartitions shouldBe partitionNumber
    partitionedRddToString(partitionedRdd) shouldBe "0-1a, 0-2b, 1-3c, 1-4d, 2-5e"
  }

}
