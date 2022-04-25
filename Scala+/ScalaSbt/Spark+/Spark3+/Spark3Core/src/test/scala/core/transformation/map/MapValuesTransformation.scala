package core.transformation.map

import core.Factory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class MapValuesTransformation extends AnyFlatSpec with Matchers {

  "mapValues" should "map only values" in {
    val str = Factory.sc.parallelize(Seq((1, "a"), (2, "b")))
      .mapValues(value => value.toUpperCase())
      .map(tuple => tuple._1 + tuple._2)
      .reduce((str1, str2) => str1 + str2)
    str shouldBe "1A2B"
  }

}
