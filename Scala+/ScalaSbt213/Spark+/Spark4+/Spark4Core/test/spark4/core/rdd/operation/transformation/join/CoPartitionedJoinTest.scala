package spark4.core.rdd.operation.transformation.join

import org.apache.spark.HashPartitioner
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark4.core.Factory

class CoPartitionedJoinTest extends AnyFlatSpec with Matchers {
  private val namesSeq: Seq[(Long, String)] = Seq((1L, "John"), (2L, "Mary"), (3L, "Nick"))
  private val agesSeq: Seq[(Long, Int)] = Seq((2L, 25), (1L, 35), (4L, 40))

  it should "do NOT co-partitioned join" in {
    val names = Factory.sc.parallelize(namesSeq, numSlices = 2)
    val ages = Factory.sc.parallelize(agesSeq, numSlices = 3)
    names.partitioner shouldBe empty
    ages.partitioner shouldBe empty
    val joined = names.join(ages)
    val debug = joined.toDebugString
    println(debug)
    debug should include("CoGroupedRDD")
    joined.collect should contain inOrderOnly(
      (1, ("John", 35)),
      (2, ("Mary", 25))
    )
  }

  it should "do co-partitioned join" in {
    val partitioner = new HashPartitioner(2)
    val names = Factory.sc.parallelize(namesSeq).partitionBy(partitioner).persist()
    val ages = Factory.sc.parallelize(agesSeq).partitionBy(partitioner).persist()
    names.partitioner shouldEqual ages.partitioner
    val joined = names.join(ages)
    val debug = joined.toDebugString
    println(debug)
    debug should include("CoGroupedRDD")
    joined.collect should contain inOrderOnly(
      (2, ("Mary", 25)),
      (1, ("John", 35)),
    )
  }

}
