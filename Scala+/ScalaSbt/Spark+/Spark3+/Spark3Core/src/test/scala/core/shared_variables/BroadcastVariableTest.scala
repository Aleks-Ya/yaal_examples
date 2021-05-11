package core.shared_variables

import core.Factory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class BroadcastVariableTest extends AnyFlatSpec with Matchers {

  it should "use a broadcast variable" in {
    val expVar = Array(1, 2, 3)
    val broadcastVar = Factory.sc.broadcast(expVar)
    broadcastVar.getClass.getName shouldEqual "org.apache.spark.broadcast.TorrentBroadcast"

    val actVar = broadcastVar.value
    actVar.getClass shouldBe classOf[Array[Int]]
    actVar shouldEqual expVar
  }
}
