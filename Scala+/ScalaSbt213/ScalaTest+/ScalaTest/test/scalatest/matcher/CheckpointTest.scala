package scalatest.matcher

import org.scalatest.Checkpoints
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * Report several failed assertions together
 */
class CheckpointTest extends AnyFlatSpec with Matchers with Checkpoints {

  it should "check string equality" in {
    val cp = new Checkpoint
    cp {
      10 shouldEqual 20
    }
    cp {
      "foo" shouldEqual "bar"
    }
    cp("aaa" shouldEqual "bbb")
    cp.reportAll()
  }

}