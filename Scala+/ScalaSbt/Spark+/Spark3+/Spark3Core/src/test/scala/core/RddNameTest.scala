package core

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class RddNameTest extends AnyFlatSpec with Matchers {

  it should "set RDD name" in {
    val rddName = "my_rdd"
    val rdd = Factory.sc.parallelize(Seq(1, 2, 3)).setName(rddName)
    rdd.toString() should include(s"$rddName ParallelCollectionRDD[0] at parallelize at RddName.scala")
    rdd.name shouldEqual rddName
  }

}
