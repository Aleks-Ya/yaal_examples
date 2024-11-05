package core.log

import core.Factory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class RddToDebugStringTest extends AnyFlatSpec with Matchers {
  it should "print toDebugString" in {
    val rdd = Factory.sc.parallelize(Seq(1, 2, 3)).map(_ * 2).filter(_ > 1)
    rdd.toDebugString shouldEqual
      """(1) MapPartitionsRDD[2] at filter at RddToDebugStringTest.scala:9 []
        | |  MapPartitionsRDD[1] at map at RddToDebugStringTest.scala:9 []
        | |  ParallelCollectionRDD[0] at parallelize at RddToDebugStringTest.scala:9 []""".stripMargin
  }

  it should "union" in {
    val rdd1 = Factory.sc.parallelize(Seq(1, 2, 3))
    val rdd2 = Factory.sc.parallelize(Seq(4, 5, 6))
    val rdd = rdd1.union(rdd2)
    rdd.toDebugString shouldEqual
      """(2) UnionRDD[2] at union at RddToDebugStringTest.scala:19 []
        | |  ParallelCollectionRDD[0] at parallelize at RddToDebugStringTest.scala:17 []
        | |  ParallelCollectionRDD[1] at parallelize at RddToDebugStringTest.scala:18 []""".stripMargin
  }

  it should "reduceByKey" in {
    val rdd = Factory.sc.parallelize(Seq(("John", 1000), ("Mary", 500), ("John", 200)))
      .reduceByKey(_ + _)
    rdd.toDebugString shouldEqual
      """(1) ShuffledRDD[1] at reduceByKey at RddToDebugStringTest.scala:28 []
        | +-(1) ParallelCollectionRDD[0] at parallelize at RddToDebugStringTest.scala:27 []""".stripMargin
  }

  it should "set name" in {
    val rdd = Factory.sc
      .parallelize(Seq(1, 2, 3)).setName("The 1st RDD")
      .map(_ * 2).setName("The 2nd RDD")
    rdd.toDebugString shouldEqual
      """(1) The 2nd RDD MapPartitionsRDD[1] at map at RddToDebugStringTest.scala:37 []
        | |  The 1st RDD ParallelCollectionRDD[0] at parallelize at RddToDebugStringTest.scala:36 []""".stripMargin
  }
}
