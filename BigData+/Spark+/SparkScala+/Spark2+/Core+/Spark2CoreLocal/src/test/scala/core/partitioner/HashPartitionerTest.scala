package core.partitioner

import core.Factory
import org.apache.spark.HashPartitioner
import org.apache.spark.rdd.RDD
import org.scalatest.{FlatSpec, Matchers}

class HashPartitionerTest extends FlatSpec with Matchers {
  val data = Seq((1, "a"), (2, "b"), (3, "c"), (4, "d"))
  val partitionNumber = 3

  it should "Use HashPartitioner explicitly (instance of HashPartitioner class)" in {
    val partitioner = new HashPartitioner(partitionNumber)
    val rdd = Factory.sc.parallelize(data).partitionBy(partitioner)
    rdd.getNumPartitions shouldBe partitionNumber
    partitionedRddToString(rdd) shouldBe "0-3-c, 1-1-a, 1-4-d, 2-2-b"
  }

  it should "Use HashPartitioner implicitly (RDD#repartition)" in {
    val rdd = Factory.sc.parallelize(data).repartition(partitionNumber)
    rdd.getNumPartitions shouldBe partitionNumber
    partitionedRddToString(rdd) shouldBe "0-3-c, 1-1-a, 1-4-d, 2-2-b"
  }

  private def partitionedRddToString(rdd: RDD[(Int, String)]) = {
    rdd.mapPartitionsWithIndex((partitionIndex, tuples) =>
      tuples.map(tuple => s"$partitionIndex-${tuple._1}-${tuple._2}")
    ).reduce((str1, str2) => s"$str1, $str2")
  }
}
