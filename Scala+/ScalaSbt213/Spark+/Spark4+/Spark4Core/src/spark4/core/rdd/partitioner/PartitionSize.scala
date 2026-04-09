package spark4.core.rdd.partitioner

import org.apache.spark.rdd.RDD

object PartitionSize {
  def partitionSizes(rdd: RDD[_]): Map[Int, Int] =
    rdd.mapPartitionsWithIndex((index, iter) => Iterator(index -> iter.size)).collect.toMap
}
