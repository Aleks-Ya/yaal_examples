package spark3.sql.dataframe.operation.transformation

import org.apache.spark.sql.{DataFrame, Dataset, Row}
import org.scalatest.flatspec.AnyFlatSpec
import spark3.sql.{Factory, SparkMatchers}

class CoalesceTransformationTest extends AnyFlatSpec with SparkMatchers {

  it should "reduce partitions number" in {
    val df: DataFrame = Factory.peopleDf.repartition(3)
    df.rdd.getNumPartitions shouldEqual 3
    val ds2: Dataset[Row] = df.coalesce(2)
    ds2.rdd.getNumPartitions shouldEqual 2
  }

  it should "increase partitions number (do nothing)" in {
    val df: DataFrame = Factory.peopleDf.repartition(3)
    df.rdd.getNumPartitions shouldEqual 3
    val ds2: Dataset[Row] = df.coalesce(10)
    ds2.rdd.getNumPartitions shouldEqual 3
  }

}
