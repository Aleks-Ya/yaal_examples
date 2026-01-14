package core.shared_variables

import factory.Factory
import org.apache.spark.util.LongAccumulator
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * Pass several accumulators as a single object.
 */
class GroupOfAccumulatorsTest extends AnyFlatSpec with Matchers {

  it should "use a group of accumulators" in {
    val successCount = Factory.sc.longAccumulator("successCount")
    val failureCount = Factory.sc.longAccumulator("failureCount")
    val accumulators = Accumulators(successCount, failureCount)

    Factory.sc.parallelize(Array(1, 2, 3, 4, 5)).foreach(x => {
      if (x % 2 == 0) {
        accumulators.evenCount.add(1)
      } else {
        accumulators.oddCount.add(1)
      }
    })
    accumulators.evenCount.value shouldEqual 2
    accumulators.oddCount.value shouldEqual 3
  }

}

case class Accumulators(evenCount: LongAccumulator, oddCount: LongAccumulator)
