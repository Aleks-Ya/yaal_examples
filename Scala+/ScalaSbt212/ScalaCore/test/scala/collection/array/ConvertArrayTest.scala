package scala.collection.array

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.collection.mutable

class ConvertArrayTest extends AnyFlatSpec with Matchers {

  it should "convert an Array to mutable HashSet" in {
    val arr = Array(1, 2, 3)
    val hashSet = mutable.HashSet.empty[Int] ++= arr
    hashSet should contain allElementsOf arr
  }

}
