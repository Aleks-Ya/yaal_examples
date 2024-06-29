package dataframe.show

import factory.Factory
import org.apache.spark.sql.functions.{broadcast, col}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class ExplainDfTest extends AnyFlatSpec with Matchers {

  it should "explain a drop transformation" in {
    val df = Factory.peopleDf.drop("age")
    df.explain()
  }

  it should "explain a filter transformation" in {
    val df = Factory.peopleDf.filter(col("age") > 18)
    df.explain()
  }

  it should "explain a broadcast variable" in {
    val minAge = 18
    val minAgeBroadcast = Factory.sc.broadcast(minAge)
    val df = Factory.peopleDf.filter(col("age") > minAgeBroadcast.value)
    df.explain()
  }

  it should "explain a broadcast join" in {
    val smallDf = Factory.peopleDf
    val bigDf = Factory.cityListDf
    val joinedDf = bigDf.join(broadcast(smallDf))
    joinedDf.explain()
  }

}