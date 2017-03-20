package core.join

import core.Factory
import org.scalatest.{FlatSpec, Matchers}

class ZipTest extends FlatSpec with Matchers {

  it should "zip two RDDs" in {
    val numbers = Factory.sc.range(1, 4)
    val chars = Factory.sc.makeRDD("abc".toCharArray)
    val zipped = chars.zip(numbers)
    zipped.foreach(println)
  }
}
