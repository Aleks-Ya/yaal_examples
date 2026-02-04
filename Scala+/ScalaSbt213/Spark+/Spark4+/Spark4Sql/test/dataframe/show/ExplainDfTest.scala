package dataframe.show

import factory.Factory
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.execution._
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

  it should "explain in a different formats" in {
    def printMode(df: DataFrame, mode: ExplainMode): Unit = {
      println(s"\n\n-------------------- Mode: ${mode.name} --------------------")
      df.explain(mode.name)
    }

    val df = Factory.peopleDf.drop("age")
    printMode(df, SimpleMode)
    printMode(df, ExtendedMode)
    printMode(df, CodegenMode)
    printMode(df, CostMode)
    printMode(df, FormattedMode)
  }

}