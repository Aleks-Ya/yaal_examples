package core.transformation.join

import core.Factory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ZipTest extends AnyFlatSpec with Matchers {

  it should "zip two RDDs" in {
    val numbers = Factory.sc.range(1, 4)
    val chars = Factory.sc.makeRDD("abc".toCharArray)
    val zipped = chars.zip(numbers)
    zipped.collect() should contain inOrderOnly(('a', 1), ('b', 2), ('c', 3))
  }

}
