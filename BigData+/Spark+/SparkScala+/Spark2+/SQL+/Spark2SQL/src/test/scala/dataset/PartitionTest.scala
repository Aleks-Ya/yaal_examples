package dataset

import factory.Factory
import org.apache.spark.sql.{Encoder, Encoders, SparkSession}
import org.scalatest.{FlatSpec, Matchers}

class PartitionTest extends FlatSpec with Matchers {

  private val ss: SparkSession = Factory.ss

  import ss.sqlContext.implicits._

  it should "get partition number of a Dataset" in {
    val exp = Seq("c", "d")
    val ds = exp.toDS()
    ds.rdd.getNumPartitions shouldEqual 1
  }

  it should "change partition number" in {
    val exp = Seq("c", "d")
    val ds = exp.toDS()
    ds.rdd.getNumPartitions shouldEqual 1
    val ds2 = ds.repartition(2)
    ds2.rdd.getNumPartitions shouldEqual 2
  }

}


private case class PeoplePojo(name: String, age: Int) {}
