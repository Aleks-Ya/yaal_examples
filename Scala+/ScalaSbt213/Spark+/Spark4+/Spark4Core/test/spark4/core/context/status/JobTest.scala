package spark4.core.context.status

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark4.core.Factory

class JobTest extends AnyFlatSpec with Matchers {

  it should "get job id" in {
    Factory.sc.statusTracker.getJobIdsForGroup(null) shouldBe empty
    Factory.sc.emptyRDD.count shouldEqual 0
    val jobIds = Factory.sc.statusTracker.getJobIdsForGroup(null)
    jobIds should contain only 0
  }

}
