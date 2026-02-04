package dataset.transformation

import factory.{City, Factory}
import org.apache.spark.sql.{Dataset, Encoder, Encoders}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class MapPartitionsTransformationTest extends AnyFlatSpec with Matchers {

  it should "map a Dataset by partitions" in {
    val ds: Dataset[City] = Factory.cityDs.repartition(2)
    implicit val encoder: Encoder[City] = Encoders.product[City]
    val mappedDs = ds.mapPartitions(partition =>
      partition.map(city =>
        City(city.name.toUpperCase, city.establishYear - 1)
      )
    )
    mappedDs.collect() should contain allOf(
      City("MOSCOW", 1146),
      City("SPB", 1702),
      City("NEW YORK", 1664))
  }

}
