package dataframe.join

import factory.Factory
import org.scalatest.{FlatSpec, Matchers}

class CrossJoinTest extends FlatSpec with Matchers {

  "Join 2 DFs" should "works" in {
    val peopleDf = Factory.peopleDf
    val citiesDf = Factory.citiesDf
    val joined = peopleDf.crossJoin(citiesDf)
    joined.show
    joined.printSchema()
  }
}