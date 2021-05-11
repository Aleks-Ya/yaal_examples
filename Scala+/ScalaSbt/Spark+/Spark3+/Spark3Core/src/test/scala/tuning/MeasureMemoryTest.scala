package tuning

import core.Factory
import org.apache.spark.util.SizeEstimator
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class MeasureMemoryTest extends AnyFlatSpec with Matchers {


  it should "measure size of an object" in {
    SizeEstimator.estimate("a") shouldEqual 48
    SizeEstimator.estimate(classOf[MeasureMemoryTest]) shouldEqual 0
    SizeEstimator.estimate(classOf[MeasureMemoryTest]) shouldEqual 0
    val data = Array(1, 2, 3)
    //    println(stringSize)
    val rdd = Factory.sc.parallelize(data)
    SizeEstimator.estimate(data) shouldEqual 32
    SizeEstimator.estimate(rdd) shouldEqual 1502696
    SizeEstimator.estimate(rdd.collect()) shouldEqual 0
//      .foreach(element => {
//        println(element)
//      })
  }


}
