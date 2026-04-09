package spark3.core.context.status

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark3.core.Factory

class StageTest extends AnyFlatSpec with Matchers {

  it should "create a job having 2 stages" in {
    Factory.sc
      .parallelize(Seq(1, 10))
      .repartition(2)
      .reduce(_ + _) shouldEqual 11

    val jobIds = Factory.sc.statusTracker.getJobIdsForGroup(null)
    jobIds should have size 1
    val jobId = jobIds.head
    val jobInfo = Factory.sc.statusTracker.getJobInfo(jobId).get
    val stageIds = jobInfo.stageIds()
    stageIds should have size 2
  }

}
