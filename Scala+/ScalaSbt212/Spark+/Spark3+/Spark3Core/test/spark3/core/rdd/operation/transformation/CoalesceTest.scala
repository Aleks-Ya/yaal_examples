package spark3.core.rdd.operation.transformation

import org.apache.spark.rdd.RDD
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark3.core.Factory

class CoalesceTest extends AnyFlatSpec with Matchers {

  it should "reduce number of partitions" in {
    val rdd: RDD[Int] = Factory.sc.parallelize(Seq(1, 2, 3)).repartition(3)
    rdd.getNumPartitions shouldEqual 3
    val rdd2: RDD[Int] = rdd.coalesce(2)
    rdd2.getNumPartitions shouldEqual 2
  }

  it should "increase number of partitions (do nothing)" in {
    val rdd: RDD[Int] = Factory.sc.parallelize(Seq(1, 2, 3)).repartition(3)
    rdd.getNumPartitions shouldEqual 3
    val rdd2: RDD[Int] = rdd.coalesce(10)
    rdd2.getNumPartitions shouldEqual 3
  }

}
