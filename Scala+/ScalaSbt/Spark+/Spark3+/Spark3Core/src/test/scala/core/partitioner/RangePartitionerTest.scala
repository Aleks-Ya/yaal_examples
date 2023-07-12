package core.partitioner

import core.Factory
import org.apache.spark.RangePartitioner
import org.apache.spark.rdd.RDD
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

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

  private def partitionedRddToString(rdd: RDD[(Int, String)]) = {
    rdd.mapPartitionsWithIndex((partitionIndex, tuples) =>
      tuples.map(tuple => s"$partitionIndex-${tuple._1}${tuple._2}")
    ).reduce((str1, str2) => s"$str1, $str2")
  }
}
