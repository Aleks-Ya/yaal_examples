package spark3.sql.dataframe.operation.transformation

import org.apache.spark.sql.functions.col
import org.apache.spark.sql.{DataFrame, Dataset, Row}
import org.scalatest.flatspec.AnyFlatSpec
import spark3.sql.{Factory, SparkMatchers}

class RepartitionTransformationTest extends AnyFlatSpec with SparkMatchers {

  it should "change partitions number" in {
    val df: DataFrame = Factory.peopleDf
    df.rdd.getNumPartitions shouldEqual 1
    val ds: Dataset[Row] = df.repartition(2)
    ds.rdd.getNumPartitions shouldEqual 2
  }

  it should "repartition by expression" in {
    val df: DataFrame = Factory.createDf("name STRING, city STRING",
      Row("John", "London"),
      Row("Mary", "Paris"),
      Row("Mark", "Berlin"),
      Row("Anna", "Berlin"),
      Row("Matt", "Paris"),
      Row("Todd", "Berlin"),
      Row("Mike", null),
      Row(null, null))
    df.rdd.getNumPartitions shouldEqual 1
    val updatedDf: DataFrame = df.repartition(2, col("city"))
    updatedDf.rdd.getNumPartitions shouldEqual 2
    updatedDf.rdd.mapPartitionsWithIndex((partition, rows) => rows.map(row => (partition, row))).collect()
      .foreach {
        case (index, row) => println(s"Partition: $index, Row: $row")
      }
  }

}