package core.transformation.join

import core.Factory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class JoinTest extends AnyFlatSpec with Matchers {

  it should "join two RDDs" in {
    val names = Factory.sc.parallelize(Seq((1L, "John"), (2L, "Mary")))
    val ages = Factory.sc.parallelize(Seq((2L, 25), (1L, 35)))
    val joined = names.join(ages)
    joined.collect() should contain inOrderOnly((1, ("John", 35)), (2, ("Mary", 25)))
  }

}
