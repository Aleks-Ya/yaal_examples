package spark3.sql.dataset.transformation

import org.apache.spark.sql.Dataset
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark3.sql.{City, Factory}

class RepartitionTransformationTest extends AnyFlatSpec with Matchers {

  it should "change partition number" in {
    val ds: Dataset[City] = Factory.cityDs
    ds.rdd.getNumPartitions shouldEqual 1
    val ds2: Dataset[City] = ds.repartition(2)
    ds2.rdd.getNumPartitions shouldEqual 2
  }

}
