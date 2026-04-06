package spark4.core.rdd.operation.transformation.group

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark4.core.Factory

class GroupByTest extends AnyFlatSpec with Matchers {

  "Plain RDD" should "group by" in {
    val rdd = Factory.sc.parallelize(Seq("John", "Mary", "Ann"))
      .groupBy(_.length)
    rdd.collect should contain inOrderOnly(
      (4, Seq("John", "Mary")),
      (3, Seq("Ann"))
    )
  }

  "Pair RDD" should "group by key" in {
    val rdd = Factory.sc.parallelize(Seq(("London", "John"), ("Paris", "Mary"), ("London", "Mark")))
      .groupBy(_._1)
    rdd.collect should contain inOrderOnly(
      ("London", Seq(("London", "John"), ("London", "Mark"))),
      ("Paris", Seq(("Paris", "Mary")))
    )
  }

  "Pair RDD" should "group by value" in {
    val rdd = Factory.sc.parallelize(Seq(("London", "John"), ("Paris", "Mary"), ("Berlin", "John")))
      .groupBy(_._2)
    rdd.collect should contain inOrderOnly(
      ("Mary", Seq(("Paris", "Mary"))),
      ("John", Seq(("London", "John"), ("Berlin", "John")))
    )
  }

}
