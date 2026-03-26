package spark3.core.transformation.group

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark3.core.Factory

class CoGroupTest extends AnyFlatSpec with Matchers {

  it should "group values from multiple RDDs" in {
    val rdd1 = Factory.sc.parallelize(Seq(("London", "John"), ("Paris", "Mary"), ("London", "Mark")))
    val rdd2 = Factory.sc.parallelize(Seq(("London", "UK"), ("Paris", "France"), ("Berlin", "Germany")))
    val rdd = rdd1.cogroup(rdd2)
    rdd.collect should contain theSameElementsAs Seq(
      ("London", (Seq("John", "Mark"), Seq("UK"))),
      ("Paris", (Seq("Mary"), Seq("France"))),
      ("Berlin", (Seq(), Seq("Germany")))
    )
  }

}
