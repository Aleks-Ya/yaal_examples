package core.decomposition

import core.Factory
import org.apache.spark.rdd.RDD
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ExtractTaskInClass extends AnyFlatSpec with Matchers {

  it should "put processing RDD into a local class" in {
    class PipelineLocal {
      def processRdd(rdd: RDD[Int]): RDD[Int] = {
        rdd.map(number => number * 2)
      }
    }

    val rdd = Factory.sc.parallelize(Seq(1, 2, 3))
    val pipeline = new PipelineLocal
    val rdd2 = pipeline.processRdd(rdd)
    val list = rdd2.collect
    list should contain inOrderOnly(2, 4, 6)
  }

  it should "put processing RDD into a not nested class" in {
    val rdd = Factory.sc.parallelize(Seq(1, 2, 3))
    val pipeline = new PipelineNotNested
    val rdd2 = pipeline.processRdd(rdd)
    val list = rdd2.collect
    list should contain inOrderOnly(2, 4, 6)
  }

}

class PipelineNotNested {
  def processRdd(rdd: RDD[Int]): RDD[Int] = {
    rdd.map(number => number * 2)
  }
}
