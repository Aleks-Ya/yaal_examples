package core.log

import core.Factory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class RddToStringTest extends AnyFlatSpec with Matchers {
  it should "print RDD content" in {
    val rdd = Factory.sc.parallelize(Seq(1, 2, 3))
    rdd.toString shouldEqual "ParallelCollectionRDD[0] at parallelize at RddToStringTest.scala:9"
    rdd.toDebugString shouldEqual "(1) ParallelCollectionRDD[0] at parallelize at RddToStringTest.scala:9 []"
    rdd.collect().mkString shouldEqual "123"
    rdd.collect().mkString(",") shouldEqual "1,2,3"
  }
}
