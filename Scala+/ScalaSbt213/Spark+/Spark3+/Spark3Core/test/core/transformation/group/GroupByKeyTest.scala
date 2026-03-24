package core.transformation.group

import factory.Factory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class GroupByKeyTest extends AnyFlatSpec with Matchers {

  "RDD" should "group by key" in {
    val rdd = Factory.sc.parallelize(Seq(("London", "John"), ("Paris", "Mary"), ("London", "Mark")))
      .groupByKey()
    rdd.collect should contain inOrderOnly(
      ("London", Seq("John", "Mark")),
      ("Paris", Seq("Mary"))
    )
  }

}
