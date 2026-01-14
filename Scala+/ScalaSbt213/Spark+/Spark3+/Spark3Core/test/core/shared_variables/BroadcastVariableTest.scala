package core.shared_variables

import factory.Factory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class BroadcastVariableTest extends AnyFlatSpec with Matchers {

  it should "use a Int variable" in {
    val addendum = 10
    val broadcastVar = Factory.sc.broadcast(addendum)
    val sum = Factory.sc.parallelize(Array(1, 2, 3)).map(x => x + broadcastVar.value).sum()
    sum shouldEqual 11 + 12 + 13
  }

  it should "use an array broadcast variable" in {
    val expVar = Array(1, 2, 3)
    val broadcastVar = Factory.sc.broadcast(expVar)
    broadcastVar.getClass.getName shouldEqual "org.apache.spark.broadcast.TorrentBroadcast"

    val actVar = broadcastVar.value
    actVar.getClass shouldBe classOf[Array[Int]]
    actVar shouldEqual expVar
  }

  it should "delete unused broadcast variable" in {
    val addendum = 10
    val broadcastVar = Factory.sc.broadcast(addendum)
    val sum = Factory.sc.parallelize(Array(1, 2, 3)).map(x => x + broadcastVar.value).sum()
    broadcastVar.unpersist()
    sum shouldEqual 11 + 12 + 13
  }
}
