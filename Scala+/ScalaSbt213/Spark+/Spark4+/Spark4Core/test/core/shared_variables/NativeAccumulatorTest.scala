package core.shared_variables

import factory.Factory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
  * Use native accumulators.
  */
class NativeAccumulatorTest extends AnyFlatSpec with Matchers {

  it should "use a Double accumulator" in {
    val accum = Factory.sc.doubleAccumulator("My Double Accumulator")

    Factory.sc.parallelize(Array(1, 2, 3, 4)).foreach(x => accum.add(x))

    accum.value shouldEqual 10
    accum.count shouldEqual 4
    accum.avg shouldEqual 2.5
    accum.sum shouldEqual 10

    accum.isZero shouldBe false
    accum.reset()
    accum.isZero shouldBe true
  }

  it should "use a Long accumulator" in {
    val accum = Factory.sc.longAccumulator("My Long Accumulator")

    Factory.sc.parallelize(Array(1, 2, 3, 4)).foreach(x => accum.add(x))

    accum.value shouldEqual 10
    accum.count shouldEqual 4
    accum.avg shouldEqual 2.5
    accum.sum shouldEqual 10

    accum.isZero shouldBe false
    accum.reset()
    accum.isZero shouldBe true
  }

  it should "use a collection accumulator" in {
    val accum = Factory.sc.collectionAccumulator[String]("My Collection Accumulator")

    Factory.sc.parallelize(Array(1, 2, 3, 4)).foreach(x => accum.add(x.toString))

    accum.value should contain allOf("1", "2", "3", "4")

    accum.isZero shouldBe false
    accum.reset()
    accum.isZero shouldBe true
  }


}
