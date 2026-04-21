package spark3.core.context.status

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark3.core.Factory

class JobTest extends AnyFlatSpec with Matchers {
  private val sc = Factory.sc

  it should "set job group and description" in {
    sc.setJobGroup("groupId1", "Job Description 1")
    sc.getLocalProperty("spark.jobGroup.id") shouldBe "groupId1"
    sc.getLocalProperty("spark.job.description") shouldBe "Job Description 1"

    sc.clearJobGroup()
    sc.getLocalProperty("spark.jobGroup.id") shouldBe null
    sc.getLocalProperty("spark.job.description") shouldBe null
  }

  it should "set job description" in {
    sc.setJobDescription("Job Description 2")
    sc.getLocalProperty("spark.job.description") shouldBe "Job Description 2"

    sc.setJobDescription(null)
    sc.getLocalProperty("spark.job.description") shouldBe null
  }

  it should "add job tag" in {
    sc.getJobTags() shouldBe empty
    sc.addJobTag("tag1")
    sc.addJobTag("tag2")
    sc.addJobTag("tag3")
    sc.getJobTags() should contain only("tag1", "tag2", "tag3")
    sc.removeJobTag("tag1")
    sc.getJobTags() should contain only("tag2", "tag3")
    sc.clearJobTags()
    sc.getJobTags() shouldBe empty
  }

  it should "get job id for job group" in {
    sc.statusTracker.getJobIdsForGroup(null) shouldBe empty
    sc.emptyRDD.count shouldEqual 0
    val jobIds = sc.statusTracker.getJobIdsForGroup(null)
    jobIds should contain only 0
  }


}
