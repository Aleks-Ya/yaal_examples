package awaitility

import org.awaitility.Awaitility.await
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.util.concurrent.TimeUnit
import scala.language.postfixOps

class WaitUntilTest extends AnyFlatSpec with Matchers {
  it should "wait until condition" in {
    await().atMost(10, TimeUnit.SECONDS).until(() => Math.random() > 0.9)
  }
}