package dataframe.join

import dataframe.DfFactory
import org.scalatest.{FlatSpec, Matchers}

class CrossJoinTest extends FlatSpec with Matchers {

  "Join 2 DFs" should "works" in {
    val peopleDf = DfFactory.peopleDf
    val citiesDf = DfFactory.citiesDf
    val joined = peopleDf.crossJoin(citiesDf)
    joined.show
    joined.printSchema()
  }
}