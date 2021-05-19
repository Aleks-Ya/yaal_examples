package dataframe.join

import factory.Factory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class CrossJoinTest extends AnyFlatSpec with Matchers {

  "Join 2 DFs" should "works" in {
    val peopleDf = Factory.peopleDf
    val citiesDf = Factory.cityListDf
    val joined = peopleDf.crossJoin(citiesDf)
    joined.show
    joined.printSchema()
  }
}