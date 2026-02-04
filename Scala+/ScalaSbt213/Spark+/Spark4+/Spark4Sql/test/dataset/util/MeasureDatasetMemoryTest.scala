package dataset.util

import factory.Factory.createCityDs
import factory.{City, Factory}
import org.apache.spark.storage.StorageLevel
import org.apache.spark.util.SizeEstimator
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class MeasureDatasetMemoryTest extends AnyFlatSpec with Matchers {

  it should "measure size of an Dataset" in {
    val ds = Factory.cityDs
    val sizeKb = SizeEstimator.estimate(ds) / 1024
    println(s"Size: $sizeKb")
    sizeKb should be(4500L +- 500L)
  }

  it should "measure size of an persisted Dataset" in {
    val cityNumber = 10000
    val cities = for (i <- 1 to cityNumber) yield City(s"City $i", 1000 + i)

    val ds = createCityDs(cities)
    val sizeKb = SizeEstimator.estimate(ds) / 1024
    println(s"Size: $sizeKb")
    sizeKb should be(5500L +- 500L)

    val dsPersisted = ds.persist(StorageLevel.DISK_ONLY)
    val sizePersistedKb = SizeEstimator.estimate(dsPersisted) / 1024
    println(s"Size persisted: $sizePersistedKb")
    sizePersistedKb should be(5500L +- 500L)
  }

}
