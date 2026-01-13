package dataframe.transformation

import factory.Factory
import org.apache.spark.sql.{DataFrame, Dataset, Row}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class RepartitionTransformationTest extends AnyFlatSpec with Matchers {

  it should "change partition number" in {
    val df: DataFrame = Factory.peopleDf
    df.rdd.getNumPartitions shouldEqual 1
    val ds2: Dataset[Row] = df.repartition(2)
    ds2.rdd.getNumPartitions shouldEqual 2
  }

}
