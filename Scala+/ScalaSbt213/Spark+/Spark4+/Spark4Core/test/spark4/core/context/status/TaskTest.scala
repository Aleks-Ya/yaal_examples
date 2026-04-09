package spark4.core.context.status

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark4.core.Factory

class TaskTest extends AnyFlatSpec with Matchers {

  it should "create a job having a stage with 2 tasks" in {
    val tasksNumber = 2
    Factory.sc.parallelize(Seq(1, 10, 100), numSlices = tasksNumber).count shouldBe 3

    val jobIds = Factory.sc.statusTracker.getJobIdsForGroup(null)
    jobIds should have size 1
    val jobId = jobIds.head
    val jobInfo = Factory.sc.statusTracker.getJobInfo(jobId).get
    val stageIds = jobInfo.stageIds()
    stageIds should have size 1
    val stageId = stageIds.head
    val stageInfo = Factory.sc.statusTracker.getStageInfo(stageId).get
    stageInfo.numTasks shouldEqual tasksNumber
  }

}
