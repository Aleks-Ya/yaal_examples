package scala.collection.set

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.collection.immutable.HashSet
import scala.collection.mutable

class HashSetTest extends AnyFlatSpec with Matchers {

  it should "create a immutable hash set" in {
    val set = HashSet(1, 2, 3)
    set should contain allOf(1, 2, 3)
  }

  it should "create a mutable hash set" in {
    val set = mutable.HashSet(1, 2, 3)
    set should contain allOf(1, 2, 3)
  }

}
