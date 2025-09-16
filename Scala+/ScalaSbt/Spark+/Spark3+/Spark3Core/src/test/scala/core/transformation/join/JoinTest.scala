package core.transformation.join

import factory.Factory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class JoinTest extends AnyFlatSpec with Matchers {

  it should "do inner join of two RDDs" in {
    val names = Factory.sc.parallelize(Seq((1L, "John"), (2L, "Mary"), (3L, "Nick")))
    val ages = Factory.sc.parallelize(Seq((2L, 25), (1L, 35), (4L, 40)))
    val joined = names.join(ages)
    joined.collect() should contain inOrderOnly(
      (1, ("John", 35)),
      (2, ("Mary", 25))
    )
  }

  it should "do full outer join of two RDDs" in {
    val names = Factory.sc.parallelize(Seq((1L, "John"), (2L, "Mary"), (3L, "Nick")))
    val ages = Factory.sc.parallelize(Seq((2L, 25), (1L, 35), (4L, 40)))
    val joined = names.fullOuterJoin(ages)
    joined.collect() should contain inOrderOnly(
      (4, (None, Some(40))),
      (1, (Some("John"), Some(35))),
      (3, (Some("Nick"), None)),
      (2, (Some("Mary"), Some(25))),
    )
  }

}
