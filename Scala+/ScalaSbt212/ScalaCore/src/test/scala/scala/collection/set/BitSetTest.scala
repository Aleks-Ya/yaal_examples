package scala.collection.set

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.collection.{BitSet, mutable}

class BitSetTest extends AnyFlatSpec with Matchers {

  it should "create a immutable hash set" in {
    val bitSet = BitSet(1, 2, 3)
    bitSet.contains(2) shouldBe true
  }

  it should "create a mutable hash set" in {
    val bitSet = mutable.BitSet(1, 2, 3)
    bitSet.contains(2) shouldBe true
  }

}
