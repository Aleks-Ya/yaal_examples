package scala.collection.seq

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class GroupedTest extends AnyFlatSpec with Matchers {
  it should "process a Seq by chunks" in {
    val s = Seq(1, 2, 3, 4, 5)
    val grouped = s.grouped(2)
    val summed = grouped.map(group => group.toString())
    summed.toList should contain allOf("List(1, 2)", "List(3, 4)", "List(5)")
  }
}
