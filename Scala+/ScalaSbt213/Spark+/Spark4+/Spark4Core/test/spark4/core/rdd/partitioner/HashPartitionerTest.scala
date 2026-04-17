package spark4.core.rdd.partitioner

import org.apache.spark.HashPartitioner
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark4.core.Factory
import spark4.core.Utils.partitionedRddToString

class HashPartitionerTest extends AnyFlatSpec with Matchers {
  private val data = Seq((1, "a"), (2, "b"), (3, "c"), (4, "d"))
  private val partitionNumber = 3

  it should "Use HashPartitioner explicitly (instance of HashPartitioner class)" in {
    val partitioner = new HashPartitioner(partitionNumber)
    val originRdd = Factory.sc.parallelize(data)
    originRdd.getNumPartitions shouldBe 1
    val rdd = originRdd.partitionBy(partitioner)
    rdd.getNumPartitions shouldBe partitionNumber
    partitionedRddToString(rdd) shouldBe "0-3-c, 1-1-a, 1-4-d, 2-2-b"
  }

  it should "Use HashPartitioner implicitly (RDD#repartition)" in {
    val originRdd = Factory.sc.parallelize(data)
    originRdd.getNumPartitions shouldBe 1
    val rdd = originRdd.repartition(partitionNumber)
    rdd.getNumPartitions shouldBe partitionNumber
    partitionedRddToString(rdd) shouldBe "0-1-a, 0-4-d, 1-2-b, 2-3-c"
  }

}
