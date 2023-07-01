package core.shared_variables

import core.Factory
import org.apache.spark.util.AccumulatorV2
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * Use custom accumulators (AccumulatorV2).
 */
class AccumulatorV2Test extends AnyFlatSpec with Matchers {
  it should "use a custom accumulator" in {
    val accum = new CheckStatisticsAccumulator
    Factory.sc.register(accum, "My Long Accumulator")

    Factory.sc.parallelize(Array(1, 2, 3, 4)).foreach(x => {
      if (x % 2 == 0) {
        accum.add(new CheckStatistics(1))
      }
    })
    if (accum.value.getTotalCheckCount != 2) throw new AssertionError
  }
}

class CheckStatistics() extends Serializable {
  def this(totalCheckCount: Long) = {
    this()
    this.totalCheckCount = totalCheckCount
  }

  private var totalCheckCount = 0L

  def incrementTotalChecks(): Unit = totalCheckCount += 1

  def getTotalCheckCount: Long = totalCheckCount

  def copy(): CheckStatistics = {
    new CheckStatistics(totalCheckCount)
  }
}

class CheckStatisticsAccumulator extends AccumulatorV2[CheckStatistics, CheckStatistics] {
  private var accValue: CheckStatistics = new CheckStatistics

  def this(checkStatistics: CheckStatistics) = {
    this()
    this.accValue = checkStatistics
  }

  def reset(): Unit = {
    accValue = new CheckStatistics()
  }

  def add(v: CheckStatistics): Unit = {
    val totalCheckCount = accValue.getTotalCheckCount + v.getTotalCheckCount
    accValue = new CheckStatistics(totalCheckCount)
  }

  override def isZero: Boolean = accValue.getTotalCheckCount == 0

  override def copy(): AccumulatorV2[CheckStatistics, CheckStatistics] = new CheckStatisticsAccumulator(accValue.copy())

  override def merge(other: AccumulatorV2[CheckStatistics, CheckStatistics]): Unit = {
    add(other.value)
  }

  override def value: CheckStatistics = accValue
}
