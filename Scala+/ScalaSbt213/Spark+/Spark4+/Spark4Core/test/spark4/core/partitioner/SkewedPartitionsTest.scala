package spark4.core.partitioner

import org.apache.spark.HashPartitioner
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark4.core.Factory

class SkewedPartitionsTest extends AnyFlatSpec with Matchers {

  it should "create an RDD with skewed partitions (by union)" in {
    val massivePartition = Factory.sc.parallelize(1 to 10000, 1) // 1 partition with 10,000 elements
    val tinyPartitions = Factory.sc.parallelize(10001 to 10003, 3) // 3 partitions with 1 element each
    val skewedRdd = massivePartition.union(tinyPartitions)
    val sizes = PartitionSize.partitionSizes(skewedRdd)
    sizes should contain only(0 -> 10000, 1 -> 1, 2 -> 1, 3 -> 1)
  }

  it should "create an RDD with skewed partitions (by HashPartitioner)" in {
    val hotKeyData = (1 to 10000).map(i => ("hot_key", i))
    val normalData = (1 to 10).map(i => (s"normal_key_$i", i))
    val combinedData = hotKeyData ++ normalData
    val partitioner = new HashPartitioner(4)
    val skewedRdd = Factory.sc.parallelize(combinedData).partitionBy(partitioner)
    val sizes = PartitionSize.partitionSizes(skewedRdd)
    sizes should contain only(0 -> 2, 1 -> 10003, 2 -> 2, 3 -> 3)
  }

}
