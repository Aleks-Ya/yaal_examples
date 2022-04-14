package dataframe.transformation

import factory.Factory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class GroupByTest extends AnyFlatSpec with Matchers {

  it should "use groupBy transformation" in {
    val df = Factory.peopleDf.groupBy("gender").count()
    df.printSchema()
    df.show()
  }
}