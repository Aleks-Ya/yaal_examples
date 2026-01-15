package scala.collection.set

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.collection.SortedSet

class SortedSetTest extends AnyFlatSpec with Matchers {

  it should "create a immutable SortedSet" in {
    val set = SortedSet(3, 1, 2)
    set should contain inOrderOnly(1, 2, 3)
  }

  it should "zip two sorted sets" in {
    val set1: SortedSet[Int] = SortedSet(3, 1, 2)
    val set2: SortedSet[Int] = SortedSet(40, 10, 60)
    val set3: SortedSet[(Int, Int)] = set1 zip set2
    set3 should contain inOrderOnly((1, 10), (2, 40), (3, 60))
  }

}
