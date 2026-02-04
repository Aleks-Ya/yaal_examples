package core.partitioner

import factory.Factory
import org.apache.spark.rdd.RDD
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class CountElementsInPartitionTest extends AnyFlatSpec with Matchers {
  private val data = Seq((1, "a"), (2, "b"), (3, "c"), (4, "d"), (5, "e"))

  it should "count elements number in each partition" in {
    val rdd = Factory.sc.parallelize(data, numSlices = 2)
    rdd.getNumPartitions shouldBe 2
    val sizes = partitionSizes(rdd)
    sizes should contain only(0 -> 2, 1 -> 3)
  }

  private def partitionSizes(rdd: RDD[(Int, String)]): Map[Int, Int] =
    rdd.mapPartitionsWithIndex((index, iter) => Iterator(index -> iter.size)).collect().toMap

}
